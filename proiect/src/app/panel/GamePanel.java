package app.panel;

import app.client.Board;
import app.client.Dice;
import app.client.Globals;
import app.communication.Codes;
import app.communication.Net;
import app.communication.Request;
import app.server.ClientThread;
import com.sun.jdi.VoidType;
import com.sun.jdi.VoidValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class GamePanel extends JPanel {
    private Canvas canvas;

    public static final Color BOARD_EDGE_COLOR = new Color(87 ,36, 0);
    public static final Color BOARD_COLOR = new Color(116, 51, 0);
    public static final Color BOARD_IN_COLOR = new Color(213, 136, 98);
    public static final Color BOARD_WHITE_TRIANGLE = new Color(248, 184, 153, 255);
    public static final Color BOARD_BLACK_TRIANGLE = new Color(56, 22, 5);
    public static final Color PIECE_BLACK = new Color(69, 42, 21);
    public static final Color PIECE_BLACK_BORDER = new Color(31, 14, 0);
    public static final Color PIECE_WHITE = new Color(239, 206, 185);
    public static final Color PIECE_WHITE_BORDER = new Color(94, 68, 55);

    public static final int X_OFFSET = 5;
    public static final int Y_OFFSET = 3;

    private Rectangle leftSide = null;
    private Rectangle rightSide = null;

    private Board board = new Board();
    private JLabel infoText = new JLabel();

    private int boardEdgeWidth = -1;

    private int player = 1;

    boolean piecesToPlace = false;
    Rectangle placeAt = null;
    Board.PieceType pieceType = Board.PieceType.NONE;
    int countToPlace = 0;

    public void updateBoard ( String counts, String positions, Map<String, String> inParams ) {
        Arrays.stream(counts.replace("{", "").replace("}","").split(",")).forEach(e->{
            int piece = Integer.parseInt(e.split("=")[0].strip());
            int count = Integer.parseInt(e.split("=")[1].strip());

            if ( this.board.getPlayingAs().equals(Board.PieceType.BLACK) ) piece = 23 - piece;

            this.board.getPieceCountMap().put(piece,count);
        });

        Arrays.stream(positions.replace("{", "").replace("}", "").split(",")).forEach(e->{
            int piece = Integer.parseInt(e.split("=")[0].strip());
            int uid = Integer.parseInt(e.split("=")[1].strip());

            if ( this.board.getPlayingAs().equals(Board.PieceType.BLACK) ) piece = 23 - piece;

            Board.PieceType type;
            if ( uid == Integer.parseInt(Globals.getValue("userID")) ) type = this.board.getPlayingAs();
            else type = this.board.getPlayingAs().equals(Board.PieceType.WHITE) ? Board.PieceType.BLACK : Board.PieceType.WHITE;

            if ( uid == 0 ) type = Board.PieceType.NONE;

            this.board.getPieceTypeMap().put(piece, type);
        });

        for (var key : inParams.keySet()) {
            if ( key.startsWith("g") ) {
                var uid = key.substring(1);
                var g = Integer.parseInt(inParams.get(key));

                if ( uid.equals(Globals.getValue("userID")) )
                    if ( this.board.getPlayingAs().equals(Board.PieceType.WHITE) )
                        this.board.setWhiteInGraveyard( g );
                    else
                        this.board.setBlackInGraveyard( g );
                else
                    if ( this.board.getPlayingAs().equals(Board.PieceType.WHITE) )
                        this.board.setBlackInGraveyard( g );
                    else
                        this.board.setWhiteInGraveyard( g );
            }
        }

        this.repaint();
    }

    public void reinitialise () {
        this.setPlayerType( Globals.getValue("color").equals("white") ? Board.PieceType.WHITE : Board.PieceType.BLACK );

        System.out.println(Globals.getValue("counts"));
        System.out.println(Globals.getValue("players"));

        Arrays.stream(Globals.getValue("counts").replace("{", "").replace("}", "").split(",")).forEach(e->{
            int piece = Integer.parseInt(e.split("=")[0].strip());
            int count = Integer.parseInt(e.split("=")[1].strip());

            if ( this.board.getPlayingAs().equals(Board.PieceType.BLACK) ) piece = 23 - piece;

            this.board.getPieceCountMap().put(piece,count);
        });

        Arrays.stream(Globals.getValue("players").replace("{", "").replace("}", "").split(",")).forEach(e->{
            int piece = Integer.parseInt(e.split("=")[0].strip());
            int uid = Integer.parseInt(e.split("=")[1].strip());

            if ( this.board.getPlayingAs().equals(Board.PieceType.BLACK) ) piece = 23 - piece;

            Board.PieceType type;
            if (uid == Integer.parseInt(Globals.getValue("userID"))) type = this.board.getPlayingAs();
            else type = this.board.getPlayingAs().equals(Board.PieceType.WHITE) ? Board.PieceType.BLACK : Board.PieceType.WHITE;

            if ( uid == 0 ) type = Board.PieceType.NONE;

            this.board.getPieceTypeMap().put(piece, type);
        });

        this.piecesToPlace = true;
        this.repaint();

        this.startCom ();
    }

    private AtomicBoolean shouldStop = new AtomicBoolean();

    public void startCom () {
        this.shouldStop.set(false);
        Map< String , String > params = new HashMap<>();

        params.put("userID", Globals.getValue("userID"));

        Thread t = new Thread(() -> {
            var ref = new Object() {
                boolean diceRerollProt = false;
                boolean diceRerollProt2 = true;
            };
            while (! shouldStop.get()) {
                new Net().makeRequest(
                        new Request(Globals.getValue("serverIP"), Integer.parseInt(Globals.getValue("serverPort")), params),
                        Codes.clientInGameRefresh,
                        (code, response) -> {
                            setInfoMessage(response.getOrDefault("message", ""));

                            if ( code == Codes.serverUpdateBoard ) {
                                this.updateBoard(response.get("counts"), response.get("players"), response);
                            } else if ( code == Codes.serverRollHighest && ! this.board.isMovingEnabled() )
                                this.board.setRollingEnabled(true);
                            else if ( code == Codes.serverNotYourTurn && !ref.diceRerollProt) {
                                if ( response.containsKey("otherRoll") ) {
                                    Dice.getValues()[0] = Integer.parseInt(response.get("otherRoll").split("%")[0]);
                                    Dice.getValues()[1] = Integer.parseInt(response.get("otherRoll").split("%")[1]);
                                    this.board.setRollingEnabled(false);
                                    this.repaint();
                                    ref.diceRerollProt = true;
                                }
                            } else if ( code != Codes.serverNotYourTurn ) {
                                ref.diceRerollProt = false;
                            }

                            if ( code == Codes.serverSuccess && response.get("message").equals("Your Turn") && !ref.diceRerollProt2 ) {
                                this.board.setRollingEnabled(true);
                                ref.diceRerollProt2 = true;
                            } else if ( code != Codes.serverSuccess )
                                ref.diceRerollProt2 = false;

                            System.out.println(response);
                        }
                );

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();
    }

    public void setPlayerType (Board.PieceType type) {
        this.board.setPlayingAs(type);
    }

    int placeState = -1;
    int placeFrom = -1;
    int placeTo = -1;
//    List <Integer> possiblePositions;
    List <Integer> availableMoves;
    Map < Integer, List < Integer > > possiblePositions;

    boolean canGetTo (int from, int to, List < Integer > moves ) {
        if ( to > from ) return false;

        int s = Math.abs(to - from);

        for ( var i : moves )
            if ( i == s )
                return true;

        for (int i = 0; i < moves.size( ) - 1; i++)
            for ( int j = i + 1; j < moves.size(); j++ )
                if ( moves.get(i) + moves.get(j) == s )
                    return true;

        for (int i = 0; i < moves.size( ) - 2; i++)
            for ( int j = i + 1; j < moves.size() - 1; j++ )
                for ( int k = j + 1; k < moves.size(); k++ )
                    if ( moves.get(i) + moves.get(j) + moves.get(k) == s )
                        return true;

        for (int i = 0; i < moves.size( ) - 3; i++)
            for ( int j = i + 1; j < moves.size() - 2; j++ )
                for ( int k = j + 1; k < moves.size() - 1; k++ )
                    for ( int l = k + 1; l < moves.size(); l++ )
                        if ( moves.get(i) + moves.get(j) + moves.get(k) + moves.get(l) == s )
                        return true;

        return false;
    }

    void removeMovesFromTo (int from, int to, List < Integer > moves ) {
        int s = Math.abs(to - from);

        for ( var i : moves )
            if ( i == s ) {
                moves.remove(i);
                return;
            }

        for (int i = 0; i < moves.size( ) - 1; i++)
            for ( int j = i + 1; j < moves.size(); j++ )
                if ( moves.get(i) + moves.get(j) == s ) {
                    moves.remove(moves.get(j));
                    moves.remove(moves.get(i));
                    return;
                }

        for (int i = 0; i < moves.size( ) - 2; i++)
            for ( int j = i + 1; j < moves.size() - 1; j++ )
                for ( int k = j + 1; k < moves.size(); k++ )
                    if ( moves.get(i) + moves.get(j) + moves.get(k) == s ) {
                        moves.remove(moves.get(k));
                        moves.remove(moves.get(j));
                        moves.remove(moves.get(i));
                        return;
                    }

        if ( moves.size() == 4 && moves.get(0) + moves.get(1) + moves.get(2) + moves.get(3) == s ) {
            moves.clear();
        }
    }

    Map < String ,String > movePieceParam = new HashMap<>();
    int moveCount = 0;

    void drawPiecesToggle () {
        this.piecesToPlace = true;

        boolean haveAnyInGraveyard = this.board.getPlayingAs().equals(Board.PieceType.WHITE) ? (this.board.getWhiteInGraveyard() != 0) : (this.board.getBlackInGraveyard() != 0);
        boolean canRemove = ( this.board.getPieceTypeMap().entrySet().stream().noneMatch(e-> e.getValue() == board.getPlayingAs() && e.getKey() >= 6) ) &&
                ( this.board.getPlayingAs().equals(Board.PieceType.WHITE) ? this.board.getWhiteInGraveyard() == 0 : this.board.getBlackInGraveyard() == 0 );

        Function < Integer, Integer > decreaseGraveyard = (Integer cnt) -> {
            if ( this.board.getPlayingAs().equals(Board.PieceType.WHITE) )
                this.board.setWhiteInGraveyard( this.board.getWhiteInGraveyard() - cnt );
            else
                this.board.setBlackInGraveyard( this.board.getBlackInGraveyard() - cnt );

            return 0;
        };

        if ( haveAnyInGraveyard && this.board.isMovingEnabled() && !canRemove ) {
            placeState = 15;
            placeFrom = 24;

            boolean atLeastOneMoveIsPossible = false;

            for ( var move : availableMoves ) {
                if ( this.board.getPieceTypeMap().get(placeFrom - move).equals(Board.PieceType.NONE) )
                    atLeastOneMoveIsPossible = true;
                if ( this.board.getPieceTypeMap().get(placeFrom - move).equals(this.board.getPlayingAs().equals(Board.PieceType.WHITE) ? Board.PieceType.BLACK : Board.PieceType.WHITE) )
                    if ( this.board.getPieceCountMap().get(placeFrom - move).equals(1) )
                        atLeastOneMoveIsPossible = true;
                if ( this.board.getPieceTypeMap().get(placeFrom - move).equals(this.board.getPlayingAs()) )
                    atLeastOneMoveIsPossible = true;
            }

            if ( ! atLeastOneMoveIsPossible ) {
                placeState = -1;
                availableMoves.clear();
                moveCount = 0;
                this.board.setMovingEnabled(false);
                //send
                movePieceParam.put("userID", Globals.getValue("userID"));
                new Net().makeRequest(
                        new Request(Globals.getValue("serverIP"), Integer.parseInt(Globals.getValue("serverPort")), movePieceParam),
                        Codes.clientMoved,
                        (code, response) -> {
                            movePieceParam.clear();
                        }
                );
                return;
            }

            placeTo = this.board.getPolledPos();

            if ( this.canGetTo(placeFrom, placeTo, this.availableMoves) ) {
                placeState = 16;
            } else
                placeState = -1;

            if ( placeState == 16 ) {
                if ( this.board.getPieceTypeMap().get(placeTo).equals(Board.PieceType.NONE) ) {
                    this.board.getPieceCountMap().put(placeTo, 1);
                    decreaseGraveyard.apply(1);

                    this.board.getPieceTypeMap().put(placeTo, this.board.getPlayingAs());
                    placeState = 17;

                    boolean reverseForBlack = this.board.getPlayingAs().equals(Board.PieceType.BLACK);
                    movePieceParam.put("" + moveCount++, "t" + (reverseForBlack ? 23 - placeTo  : placeTo) + "%g");

                    this.removeMovesFromTo(placeFrom, placeTo, availableMoves);
//                    for ( var i : this.possiblePositions.get(placeTo) )
//                        this.availableMoves.remove(i);
                } else if ( this.board.getPieceTypeMap().get(placeTo).equals(this.board.getPlayingAs().equals(Board.PieceType.WHITE) ? Board.PieceType.BLACK : Board.PieceType.WHITE) ) {
                    if ( this.board.getPieceCountMap().get(placeTo) == 1 ) {
                        if ( this.board.getPlayingAs().equals(Board.PieceType.WHITE) )
                            this.board.setBlackInGraveyard(this.board.getBlackInGraveyard() + 1);
                        else
                            this.board.setWhiteInGraveyard(this.board.getWhiteInGraveyard() + 1);

                        this.board.getPieceTypeMap().put(placeTo, this.board.getPlayingAs());
                        decreaseGraveyard.apply(1);

                        this.removeMovesFromTo(placeFrom, placeTo, availableMoves);

                        boolean reverseForBlack = this.board.getPlayingAs().equals(Board.PieceType.BLACK);
                        movePieceParam.put("" + moveCount++, "r" + (reverseForBlack ? 23 - placeTo : placeTo) + "%" + "t" + (reverseForBlack ? 23 - placeTo  : placeTo) + "%g");

                        placeState = 18;
                    } else {
                        placeState = -1;
                    }
                } else if (this.board.getPieceTypeMap().get(placeTo).equals(this.board.getPlayingAs())) {
                    this.board.getPieceCountMap().put(placeTo, this.board.getPieceCountMap().get(placeTo) + 1);
                    decreaseGraveyard.apply(1);

                    this.removeMovesFromTo(placeFrom, placeTo, availableMoves);
                    placeState = 19;

                    boolean reverseForBlack = this.board.getPlayingAs().equals(Board.PieceType.BLACK);
                    movePieceParam.put("" + moveCount++, "t" + (reverseForBlack ? 23 - placeTo  : placeTo) + "%g");

                } else {
                    placeState = -1;
                }
            }

            if ( placeState == 16 || placeState == 17 || placeState == 18 || placeState == 19 )
                placeState = -1;

            if ( availableMoves.isEmpty() ) {
                moveCount = 0;
                this.board.setMovingEnabled(false);
                //send
                movePieceParam.put("userID", Globals.getValue("userID"));
                new Net().makeRequest(
                        new Request(Globals.getValue("serverIP"), Integer.parseInt(Globals.getValue("serverPort")), movePieceParam),
                        Codes.clientMoved,
                        (code, response) -> {
                             movePieceParam.clear();
                        }
                );
            }
        } else if ( this.board.isMovingEnabled() && ! haveAnyInGraveyard && ! canRemove ) {

            if ( this.board.getPolledPos() == -1 ) {
//                placeState = -1;
            } else if ( this.board.getPieceTypeMap().get(this.board.getPolledPos()).equals(this.board.getPlayingAs()) && placeState == -1 ) {
                placeState = 1;
                placeFrom = this.board.getPolledPos();
            } else if (placeState == 1) {
                if ( this.canGetTo (placeFrom ,this.board.getPolledPos(), this.availableMoves) ) {
//                if ( this.possiblePositions.containsKey(this.board.getPolledPos()) ) {
                    placeState = 2;
                    placeTo = this.board.getPolledPos();
                } else {
                    placeState = -1;
                }
            }

            if (placeState == 2) {
                if ( this.board.getPieceTypeMap().get(placeTo).equals(Board.PieceType.NONE) ) {
                    this.board.getPieceCountMap().put(placeTo, 1);
                    this.board.getPieceCountMap().put(placeFrom, this.board.getPieceCountMap().get(placeFrom) - 1);

                    if ( this.board.getPieceCountMap().get(placeFrom).equals(0))
                        this.board.getPieceTypeMap().put(placeFrom, Board.PieceType.NONE);

                    this.board.getPieceTypeMap().put(placeTo, this.board.getPlayingAs());
                    placeState = 5;

                    boolean reverseForBlack = this.board.getPlayingAs().equals(Board.PieceType.BLACK);
                    movePieceParam.put("" + moveCount++, "f" + (reverseForBlack ? 23 - placeFrom : placeFrom) + "%" + "t" + (reverseForBlack ? 23 - placeTo  : placeTo));

                    this.removeMovesFromTo(placeFrom, placeTo, availableMoves);
//                    for ( var i : this.possiblePositions.get(placeTo) )
//                        this.availableMoves.remove(i);
                } else if ( this.board.getPieceTypeMap().get(placeTo).equals(this.board.getPlayingAs().equals(Board.PieceType.WHITE) ? Board.PieceType.BLACK : Board.PieceType.WHITE) ) {
                    if ( this.board.getPieceCountMap().get(placeTo) == 1 ) {
                        if ( this.board.getPlayingAs().equals(Board.PieceType.WHITE) )
                            this.board.setBlackInGraveyard(this.board.getBlackInGraveyard() + 1);
                        else
                            this.board.setWhiteInGraveyard(this.board.getWhiteInGraveyard() + 1);

                        this.board.getPieceTypeMap().put(placeTo, this.board.getPlayingAs());
                        this.board.getPieceCountMap().put(placeFrom, this.board.getPieceCountMap().get(placeFrom) - 1);
                        if ( this.board.getPieceCountMap().get(placeFrom) == 0 )
                            this.board.getPieceTypeMap().put(placeFrom, Board.PieceType.NONE);

                        this.removeMovesFromTo(placeFrom, placeTo, availableMoves);

                        boolean reverseForBlack = this.board.getPlayingAs().equals(Board.PieceType.BLACK);
                        movePieceParam.put("" + moveCount++, "r" + (reverseForBlack ? 23 - placeTo : placeTo) + "%" + "f" + (reverseForBlack ? 23 - placeFrom : placeFrom) + "%" + "t" + (reverseForBlack ? 23 - placeTo  : placeTo));

                        placeState = 6;
                    } else {
                        placeState = -1;
                    }
                } else if (this.board.getPieceTypeMap().get(placeTo).equals(this.board.getPlayingAs())) {
                    this.board.getPieceCountMap().put(placeTo, this.board.getPieceCountMap().get(placeTo) + 1);
                    this.board.getPieceCountMap().put(placeFrom, this.board.getPieceCountMap().get(placeFrom) - 1);
                    if ( this.board.getPieceCountMap().get(placeFrom) == 0 )
                        this.board.getPieceTypeMap().put(placeFrom, Board.PieceType.NONE);

                    this.removeMovesFromTo(placeFrom, placeTo, availableMoves);
                    placeState = 7;

                    boolean reverseForBlack = this.board.getPlayingAs().equals(Board.PieceType.BLACK);
                    movePieceParam.put("" + moveCount++, "f" + (reverseForBlack ? 23 - placeFrom : placeFrom) + "%" + "t" + (reverseForBlack ? 23 - placeTo  : placeTo));

                } else {
                    placeState = -1;
                }

            }

            if ( placeState == 5 || placeState == 6 || placeState == 7 || placeState == 16 || placeState == 17 || placeState == 18 ) {
                placeState = -1;

                if ( availableMoves.isEmpty() ) {
                    moveCount = 0;
                    this.board.setMovingEnabled(false);
                    //send
                    movePieceParam.put("userID", Globals.getValue("userID"));
                    new Net().makeRequest(
                            new Request(Globals.getValue("serverIP"), Integer.parseInt(Globals.getValue("serverPort")), movePieceParam),
                            Codes.clientMoved,
                            (code, response) -> {
                                movePieceParam.clear();
                            }
                    );
                }
//                this.board.setMovingEnabled(false);
            }
        } else if ( canRemove && this.board.isMovingEnabled() ) {
            int posToRemove = this.board.getPolledPos();

            if ( posToRemove < 6 ) {

                int availableMove = availableMoves.stream().filter(e->e > posToRemove &&
                        board.getPieceTypeMap().entrySet().stream().noneMatch(
                                t->t.getKey() + 1 > posToRemove && t.getKey() < 6 && t.getValue().equals(board.getPlayingAs())
                        )
                ).findFirst().orElse(-1);

                if ( availableMove != -1 ) {
                    if ( this.board.getPieceCountMap().get(availableMove) > 0 && this.board.getPieceTypeMap().get(availableMove).equals(this.board.getPlayingAs()) ) {
                        this.board.getPieceCountMap().put(availableMove, this.board.getPieceCountMap().get(availableMove) - 1);
                        if ( this.board.getPieceCountMap().get(availableMove) == 0 )
                            this.board.getPieceTypeMap().put(availableMove, Board.PieceType.NONE);

                        movePieceParam.put("" + this.moveCount ++, "f" + availableMove);

                        availableMoves.remove(availableMove);
                    }
                }

                if ( availableMoves.isEmpty() ) {
                    moveCount = 0;
                    this.board.setMovingEnabled(false);
                    //send
                    movePieceParam.put("userID", Globals.getValue("userID"));
                    new Net().makeRequest(
                            new Request(Globals.getValue("serverIP"), Integer.parseInt(Globals.getValue("serverPort")), movePieceParam),
                            Codes.clientMoved,
                            (code, response) -> {
                                movePieceParam.clear();
                            }
                    );
                }
            }
        }

        this.repaint();
    }

    boolean diceDraw = false;
    Board.PieceType playerUser = Board.PieceType.NONE;
    Rectangle toDrawDiceIn = null;

    void drawDiceToggle (Rectangle in, Board.PieceType player) {
        this.diceDraw = true;
        this.toDrawDiceIn = in;
        this.playerUser = player;

        Dice.roll();

        Map < String , String > params = new HashMap<>();
        params.put("userID", Globals.getValue("userID"));
        params.put("diceRoll", Dice.getValues()[0] + "%" + Dice.getValues()[1]);

        this.board.setRollingEnabled(false);
        new Net().makeRequest(
                new Request(Globals.getValue("serverIP"), Integer.parseInt(Globals.getValue("serverPort")), params),
                Codes.clientRolled,
                (code, response) -> {
                    // do nothing
                    if ( code == Codes.serverMakeMove ) {
//                        this.board.setRollingEnabled(false);
                        this.board.setRollingEnabled(false);
                        this.board.setMovingEnabled(true);
                        this.availableMoves = new ArrayList<>();
                        this.availableMoves.add(Dice.getValues()[0]);
                        this.availableMoves.add(Dice.getValues()[1]);

                        if ( Dice.getValues()[0] == Dice.getValues()[1] ) {
                            this.availableMoves.add(Dice.getValues()[0]);
                            this.availableMoves.add(Dice.getValues()[1]);
                        }

                        this.repaint();
                    }
                }
        );

        this.repaint();
    }

    String infoMessage;

    void setInfoMessage (String text) {
        var requiresRepaint = this.infoMessage != null && ! this.infoMessage.equals(text);
        this.infoMessage = text;

        if (requiresRepaint)
            this.repaint();
    }

    public GamePanel() {
        this.board.setPlaceCallback(this::drawPiecesToggle);
        this.board.setRollRequestCallback(this::drawDiceToggle);
        this.setLayout(new BorderLayout());

//        this.add(infoText, BorderLayout.CENTER);
//        this.infoText.setHorizontalAlignment(SwingConstants.CENTER);
//        this.infoText.setVerticalAlignment(SwingConstants.CENTER);

        this.setInfoMessage("test");

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                board.mouseClicked(e);
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                board.mouseMoved(e);
            }
        });

        this.revalidate();
    }

    public Board getBoard() {
        return board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.drawBoard(g);
    }

    public void drawPieces(Graphics g) {
        for (int i = 0; i < 24; i++ ) {
            this.placeAt = this.board.getPiecePositionMap().get(i);
            this.countToPlace = this.board.getPieceCountMap().get(i);
            this.pieceType = this.board.getPieceTypeMap().get(i);

//            g.setColor(this.pieceType.equals(Board.PieceType.WHITE) ? PIECE_WHITE : PIECE_BLACK);
            var borderColor = this.pieceType.equals(Board.PieceType.WHITE) ? PIECE_WHITE_BORDER : PIECE_BLACK_BORDER;
            var color = this.pieceType.equals(Board.PieceType.WHITE) ? PIECE_WHITE : PIECE_BLACK;

            if ( this.pieceType.equals(Board.PieceType.NONE) )
                continue;

            int pieceRadius = this.leftSide.width / 6 - 10;
            var maxBeforeOverflow = (this.leftSide.height / 2) / pieceRadius;
            boolean isTop = this.placeAt.y == this.leftSide.y;

            int pieceHeight = 0;
            if (maxBeforeOverflow >= this.countToPlace) {
                pieceHeight = pieceRadius;
            } else {
                pieceHeight = this.placeAt.height / this.countToPlace + 20 / (this.countToPlace - 6 < 0 ? this.countToPlace : this.countToPlace - 6 <= 2 ? 7 : this.countToPlace - 6);
            }

            var l = new Point(this.placeAt.x, isTop ? this.placeAt.y : this.placeAt.y + this.placeAt.height - pieceRadius);
            while (this.countToPlace > 0) {
                g.setColor(borderColor);
                g.fillOval(l.x, l.y, pieceRadius, pieceRadius);
                g.setColor(color);
                g.fillOval(l.x, l.y, pieceRadius - 3, pieceRadius - 3);

                if (isTop)
                    l.y += pieceHeight;
                else
                    l.y -= pieceHeight;

                this.countToPlace--;
            }

//            this.piecesToPlace = false;
        }
    }

    void drawDice (Graphics g) {
        Dice.drawIn(this.toDrawDiceIn, g);
    }

    void drawBoard (Graphics g) {
        this.boardEdgeWidth = this.getWidth() / 60;

        g.setColor(BOARD_COLOR);

        g.fillRect(
                X_OFFSET,
                Y_OFFSET,
                this.getWidth() / 2 - 2 * X_OFFSET,
                this.getHeight() - 2 * Y_OFFSET
        );

        g.setColor(BOARD_EDGE_COLOR);
        g.drawRect(
                X_OFFSET,
                Y_OFFSET,
                this.getWidth() / 2 - 2 * X_OFFSET,
                this.getHeight() - 2 * Y_OFFSET
        );
        g.drawRect(
                X_OFFSET + 1,
                Y_OFFSET + 1,
                this.getWidth() / 2 - 2 * X_OFFSET - 2,
                this.getHeight() - 2 * Y_OFFSET - 2
        );

        g.drawRect(
                X_OFFSET + this.boardEdgeWidth,
                Y_OFFSET + this.boardEdgeWidth,
                this.getWidth() / 2 - 2 * (X_OFFSET + this.boardEdgeWidth),
                this.getHeight() - 2 * (Y_OFFSET + this.boardEdgeWidth)
        );

        g.drawRect(
                X_OFFSET + this.boardEdgeWidth - 1,
                Y_OFFSET + this.boardEdgeWidth - 1,
                this.getWidth() / 2 - 2 * (X_OFFSET + this.boardEdgeWidth) + 2,
                this.getHeight() - 2 * (Y_OFFSET + this.boardEdgeWidth) + 2
        );

        g.setColor(BOARD_COLOR);

        g.fillRect(
                X_OFFSET + this.getWidth() / 2,
                Y_OFFSET,
                this.getWidth() / 2 - 2 * X_OFFSET,
                this.getHeight() - 2 * Y_OFFSET
        );

        g.setColor(BOARD_EDGE_COLOR);
        g.drawRect(
                X_OFFSET + this.getWidth() / 2,
                Y_OFFSET,
                this.getWidth() / 2 - 2 * X_OFFSET,
                this.getHeight() - 2 * Y_OFFSET
        );
        g.drawRect(
                X_OFFSET + 1 + this.getWidth() / 2,
                Y_OFFSET + 1,
                this.getWidth() / 2 - 2 * X_OFFSET - 2,
                this.getHeight() - 2 * Y_OFFSET - 2
        );

        g.drawRect(
                X_OFFSET + this.boardEdgeWidth + this.getWidth() / 2,
                Y_OFFSET + this.boardEdgeWidth,
                this.getWidth() / 2 - 2 * (X_OFFSET + this.boardEdgeWidth),
                this.getHeight() - 2 * (Y_OFFSET + this.boardEdgeWidth)
        );

        g.drawRect(
                X_OFFSET + this.boardEdgeWidth - 1 + this.getWidth() / 2,
                Y_OFFSET + this.boardEdgeWidth - 1,
                this.getWidth() / 2 - 2 * (X_OFFSET + this.boardEdgeWidth) + 2,
                this.getHeight() - 2 * (Y_OFFSET + this.boardEdgeWidth) + 2
        );

        g.setColor(BOARD_EDGE_COLOR);

        g.fillRect(
                X_OFFSET + this.getWidth() / 2 - 2 * X_OFFSET,
                Y_OFFSET,
                2 * X_OFFSET,
                this.getHeight() - 2 * Y_OFFSET
        );

        g.setColor(BOARD_IN_COLOR);

        this.leftSide = new Rectangle(
                X_OFFSET + this.boardEdgeWidth + 1,
                Y_OFFSET + this.boardEdgeWidth + 1,
                this.getWidth() / 2 - 2 * ( X_OFFSET + this.boardEdgeWidth ) - 1,
                this.getHeight() - 2 * Y_OFFSET - 2 * this.boardEdgeWidth - 1
        );

        this.rightSide = new Rectangle(
                X_OFFSET + this.boardEdgeWidth + 1 + this.getWidth() / 2,
                Y_OFFSET + this.boardEdgeWidth + 1,
                this.getWidth() / 2 - 2 * X_OFFSET - 2 * this.boardEdgeWidth - 1,
                this.getHeight() - 2 * Y_OFFSET - 2 * this.boardEdgeWidth - 1
        );

        g.fillRect(
                leftSide.x,
                leftSide.y,
                leftSide.width,
                leftSide.height
        );

        g.fillRect(
                rightSide.x,
                rightSide.y,
                rightSide.width,
                rightSide.height
        );

        this.board.passNewSizes(leftSide, rightSide);
        this.drawTriangles(g, leftSide, rightSide);
        if ( this.piecesToPlace ) this.drawPieces (g);
        if ( this.diceDraw ) this.drawDice(g);

        this.drawGraveyard(g);

        g.setColor(BOARD_EDGE_COLOR);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        g.drawBytes(this.infoMessage.getBytes(StandardCharsets.UTF_8), 0, this.infoMessage.length(), X_OFFSET + this.boardEdgeWidth + 100, this.getHeight() / 2);

    }

    void drawGraveyard (Graphics g) {
        int xPos = this.getWidth() / 2;
        int pWidth = this.rightSide.x - ( this.leftSide.x + this.leftSide.width ) - 4;

        xPos -= pWidth / 2;

        int yPos = Y_OFFSET + pWidth + 10;

        yPos += pWidth / 2;

        for ( var i = 0; i < this.board.getWhiteInGraveyard(); i++ ) {
            g.setColor(PIECE_WHITE_BORDER);
            g.fillOval(xPos, yPos, pWidth, pWidth);
            g.setColor(PIECE_WHITE);
            g.fillOval(xPos, yPos, pWidth - 2, pWidth - 2);

            yPos += 2 * pWidth / 3;
        }

        yPos = this.getHeight() - Y_OFFSET - pWidth - 10;
        yPos -= pWidth / 2;

        for ( var i = 0; i < this.board.getBlackInGraveyard(); i++ ) {
            g.setColor(PIECE_BLACK_BORDER);
            g.fillOval(xPos, yPos, pWidth, pWidth);
            g.setColor(PIECE_BLACK);
            g.fillOval(xPos, yPos, pWidth - 2, pWidth - 2);

            yPos -= 2 * pWidth / 3;
        }
    }

    void drawTriangles(Graphics g, Rectangle left, Rectangle right) {
        int trHeight = left.height / 3;
        int [] triangleXPoints = new int[3];
        int [] triangleYPoints = new int[3];

        int counter = 0;
        var trBaseWidth = right.width / 6;

        triangleXPoints[0] = right.x + right.width;
        triangleXPoints[1] = triangleXPoints[0] - trBaseWidth;
        triangleXPoints[2] = (triangleXPoints[0] + triangleXPoints[1] )/ 2;

        triangleYPoints[0] = right.y + right.height;
        triangleYPoints[1] = right.y + right.height;
        triangleYPoints[2] = right.y + right.height - trHeight;

        for ( var i = 0; i < 6; i++ ) {
            if ( counter % 2 == 1 )
                g.setColor(BOARD_WHITE_TRIANGLE);
            else
                g.setColor(BOARD_BLACK_TRIANGLE);

            counter++;

            g.fillPolygon(triangleXPoints, triangleYPoints, 3);
            triangleXPoints[0] = triangleXPoints[1];
            triangleXPoints[1] = triangleXPoints[0] - trBaseWidth;
            triangleXPoints[2] = ( triangleXPoints[0] + triangleXPoints[1] ) / 2;
        }

        triangleXPoints[0] = left.x + left.width;
        triangleXPoints[1] = triangleXPoints[0] - trBaseWidth;
        triangleXPoints[2] = (triangleXPoints[0] + triangleXPoints[1] )/ 2;

        triangleYPoints[0] = left.y + left.height;
        triangleYPoints[1] = left.y + left.height;
        triangleYPoints[2] = left.y + left.height - trHeight;

        for ( var i = 0; i < 6; i++ ) {
            if ( counter % 2 == 1 )
                g.setColor(BOARD_WHITE_TRIANGLE);
            else
                g.setColor(BOARD_BLACK_TRIANGLE);

            counter++;

            g.fillPolygon(triangleXPoints, triangleYPoints, 3);
            triangleXPoints[0] = triangleXPoints[1];
            triangleXPoints[1] = triangleXPoints[0] - trBaseWidth;
            triangleXPoints[2] = ( triangleXPoints[0] + triangleXPoints[1] ) / 2;
        }

        triangleXPoints[0] = left.x + left.width;
        triangleXPoints[1] = triangleXPoints[0] - trBaseWidth;
        triangleXPoints[2] = (triangleXPoints[0] + triangleXPoints[1] )/ 2;

        triangleYPoints[0] = left.y;
        triangleYPoints[1] = left.y;
        triangleYPoints[2] = left.y + trHeight;

        for ( var i = 0; i < 6; i++ ) {
            if ( counter % 2 == 0 )
                g.setColor(BOARD_WHITE_TRIANGLE);
            else
                g.setColor(BOARD_BLACK_TRIANGLE);

            counter++;

            g.fillPolygon(triangleXPoints, triangleYPoints, 3);
            triangleXPoints[0] = triangleXPoints[1];
            triangleXPoints[1] = triangleXPoints[0] - trBaseWidth;
            triangleXPoints[2] = ( triangleXPoints[0] + triangleXPoints[1] ) / 2;
        }

        triangleXPoints[0] = right.x + right.width;
        triangleXPoints[1] = triangleXPoints[0] - trBaseWidth;
        triangleXPoints[2] = (triangleXPoints[0] + triangleXPoints[1] )/ 2;

        triangleYPoints[0] = right.y;
        triangleYPoints[1] = right.y;
        triangleYPoints[2] = right.y + trHeight;

        for ( var i = 0; i < 6; i++ ) {
            if ( counter % 2 == 0 )
                g.setColor(BOARD_WHITE_TRIANGLE);
            else
                g.setColor(BOARD_BLACK_TRIANGLE);

            counter++;

            g.fillPolygon(triangleXPoints, triangleYPoints, 3);
            triangleXPoints[0] = triangleXPoints[1];
            triangleXPoints[1] = triangleXPoints[0] - trBaseWidth;
            triangleXPoints[2] = ( triangleXPoints[0] + triangleXPoints[1] ) / 2;
        }
    }
}
