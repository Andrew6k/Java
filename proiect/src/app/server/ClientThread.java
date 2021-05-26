package app.server;

import app.communication.Codes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.invoke.SerializedLambda;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ClientThread extends Thread {
    private Socket s;
    private PrintWriter out;
    private BufferedReader in;

    private Map < String, String > inParams = new HashMap<>();
    private Map < String, String > outParams = new HashMap<>();

    private static Map < Integer, String > userAssoc = new HashMap<>();
    private static final Object userMutex = new Object();

    static class MatchEntry {
        public MatchEntry(int p1ID, int p2ID) { this.p1ID = p1ID; this.p2ID = p2ID; }

        public int p1ID;
        public int p2ID;

        public boolean p1Notified = false;
        public boolean p2Notified = false;
    }

    private static List < MatchEntry > matchMadeGroups = new ArrayList<>();
    public static final Object matchMadeMutex = new Object();

    static class Match {
        public enum State {
            CHOOSING_STARTER,
            CHOOSING_STARTER_FIRST_ROLLED,
            CHOOSING_STARTER_SECOND_ROLLED,
            GAME_RUNNING,
            P1_VICTORY,
            P2_VICTORY
        }

        public State state = State.CHOOSING_STARTER;

        public int p1;
        public int p2;

        public int[] p1FirstRoll = new int[2];
        public int[] p2FirstRoll = new int[2];

        public Map < Integer, Integer > piecesCounts = new HashMap<>();
        public Map < Integer, Integer > piecesPlayers = new HashMap<>();
        int p1Graveyard = 0;
        int p2Graveyard = 0;

        boolean requiresRefresh = false;

        public final Object lock = new Object();

        public int turnOf = 1;
    }

    private static List < Match > matches = new ArrayList<>();
    public static final Object matchesMutex = new Object();

    static int registerUsername (String user) throws UsernameTaken, NoUserSlot {
        synchronized (ClientThread.userMutex) {
            if (ClientThread.userAssoc.containsValue(user))
                throw new UsernameTaken();

            int userID = (int) (Math.random() * 10000);
            int repCount = 30;

            while (ClientThread.userAssoc.containsKey(userID) && repCount > 0) {
                userID = (int) (Math.random() * 10000);
                repCount--;
            }

            if (repCount == 0)
                throw new NoUserSlot();

            ClientThread.userAssoc.put(userID, user);

            return userID;
        }
    }

    static void unregisterUsername (int userID) {
        synchronized (ClientThread.userMutex) {
            ClientThread.userAssoc.remove(userID);
        }
    }

    int respCode;

    public ClientThread(Socket socket) throws IOException {
        this.s = socket;
        this.in = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
        this.out = new PrintWriter(this.s.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            var request = this.in.readLine();

            var tokens = request.split("&");
            int rId = Integer.parseInt(tokens[0]);

            for (int i = 1; i < tokens.length; i++) {
                this.inParams.put(
                    tokens[i].split("#")[0],
                    tokens[i].split("#")[1]
                );
            }

//            System.out.println(rId);
//            System.out.println(inParams);

            int resp = this.treatRequest(rId);



            var sb = new StringBuilder().append(resp).append("&");

            this.outParams.forEach((k, v) -> sb.append(k).append("#").append(v).append("&"));

            out.println(sb);
            out.flush();

            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int treatMatchmaking () {
        var userID = Integer.parseInt(this.inParams.get("userID"));

        synchronized (matchMadeMutex) {
            for (var matchMadeGroup : matchMadeGroups) {
                boolean forThis = false;
                String color = "";
                String opName="";

                if ( matchMadeGroup.p1ID == userID ) {
                    matchMadeGroup.p1Notified = true;
                    color = "white";
                    opName = userAssoc.get(matchMadeGroup.p2ID);
                    forThis = true;
                }

                if ( matchMadeGroup.p2ID == userID ) {
                    matchMadeGroup.p2Notified = true;
                    color = "black";
                    opName = userAssoc.get(matchMadeGroup.p1ID);
                    forThis = true;
                }

                if (forThis) {
                    this.outParams.put("color", color);
                    this.outParams.put("opponentName", opName);

                    if ( matchMadeGroup.p1Notified && matchMadeGroup.p2Notified ) {
                        synchronized (matchesMutex) {
                            var m = matches.stream().filter(e -> e.p1 == userID || e.p2 == userID).findFirst().orElse(null);

                            if (m != null) {
                                this.outParams.put("counts", m.piecesCounts.toString());
                                this.outParams.put("players", m.piecesPlayers.toString());
                            }
                        }
                    } else {
                        synchronized (matchesMutex) {
                            var m = new Match();

                            m.p1 = matchMadeGroup.p1ID;
                            m.p2 = matchMadeGroup.p2ID;
                            m.turnOf = m.p1;

                            for ( var i = 0; i < 24; i++ ) {
                                m.piecesCounts.put(i, 0);
                                m.piecesPlayers.put(i, 0);
                            }

                            m.piecesPlayers.put(0, m.p2);
                            m.piecesCounts.put(0, 2);

                            m.piecesPlayers.put(5, m.p1);
                            m.piecesPlayers.put(7, m.p1);

                            m.piecesCounts.put(5, 5);
                            m.piecesCounts.put(7, 3);

                            m.piecesPlayers.put(11, m.p2);

                            m.piecesCounts.put(11, 5);

                            m.piecesPlayers.put(12, m.p1);

                            m.piecesCounts.put(12, 5);

                            m.piecesPlayers.put(16, m.p2);
                            m.piecesPlayers.put(18, m.p2);

                            m.piecesCounts.put(16, 3);
                            m.piecesCounts.put(18, 5);

                            m.piecesPlayers.put(23, m.p1);

                            m.piecesCounts.put(23, 2);

                            matches.add(m);

                            this.outParams.put("counts", m.piecesCounts.toString());
                            this.outParams.put("players", m.piecesPlayers.toString());
                        }
                    }

                    if ( matchMadeGroup.p1Notified && matchMadeGroup.p2Notified )
                        matchMadeGroups.remove(matchMadeGroup);

                    return Codes.serverFoundMatch;
                }
            }
        }

        synchronized (userMutex) {
            if ( userAssoc.size() >= 2 ) {
                synchronized (matchMadeMutex) {
                    userAssoc.remove(userID);
                    var otherUserID = userAssoc.keySet().stream().findFirst().orElse(-1);
                    userAssoc.remove(otherUserID);

                    matchMadeGroups.add(new MatchEntry(userID, otherUserID));
                }
            }
        }

        return Codes.serverMatchmakingWaiting;
    }

    static Match getMatchForUID(int uID) {
        synchronized (matchesMutex) {
            return matches.stream().filter(m->m.p1 == uID || m.p2 == uID).findFirst().orElse(null);
        }
    }

    int treatInGame () {
        var uID = Integer.parseInt(inParams.get("userID"));
        var match = getMatchForUID(uID);

        synchronized (match.lock) {
            if ( match.turnOf != uID ) {
                outParams.put("message", "Wait for opponent's turn");
                if ( match.turnOf == match.p1 && match.p1FirstRoll[0] > 0 && match.p1FirstRoll[1] > 0 )
                    outParams.put("otherRoll", match.p1FirstRoll[0] + "%" + match.p1FirstRoll[1]);
                else if ( match.p2FirstRoll[0] > 0 && match.p2FirstRoll[1] > 0 )
                    outParams.put("otherRoll", match.p2FirstRoll[0] + "%" + match.p2FirstRoll[1]);
                return Codes.serverNotYourTurn;
            }

            if ( match.requiresRefresh ) {
                outParams.put("counts", match.piecesCounts.toString());
                outParams.put("players", match.piecesPlayers.toString());

                outParams.put("g" + match.p1, "" + match.p1Graveyard);
                outParams.put("g" + match.p2, "" + match.p2Graveyard);

                match.requiresRefresh = false;
                return Codes.serverUpdateBoard;
            }

            if ( match.state.equals(Match.State.CHOOSING_STARTER) || match.state.equals(Match.State.CHOOSING_STARTER_FIRST_ROLLED) ) {
                outParams.put("message", "Roll to see who begins the game");
                return Codes.serverRollHighest;
            }

            if ( match.state.equals(Match.State.GAME_RUNNING) ) {
                outParams.put("message", "Your Turn");
                return Codes.serverSuccess;
            }
        }

        return Codes.serverSuccess;
    }

    int clientMoved () {
        var uID = Integer.parseInt(inParams.get("userID"));
        var game = getMatchForUID(uID);

        synchronized (game.lock) {
            if ( game.state == Match.State.GAME_RUNNING ) {
//                System.out.println(inParams);

                for ( int i = 0; i < 4; i++ ) {
                    if ( inParams.containsKey("" + i) ) {
                        var t = inParams.get("" + i);
                        var moves = t.split("%");

                        for ( var move : moves ) {
                            if ( move.startsWith("g") ) {
                                if ( game.p1 == uID ) game.p1Graveyard --;
                                else game.p2Graveyard--;
                                continue;
                            }

                            int pieceId = Integer.parseInt(move.replace("f", "").replace("t", "").replace("r", ""));

                            if ( move.startsWith("f") ) {
                                game.piecesCounts.put(pieceId, game.piecesCounts.get(pieceId) - 1);
                                if ( game.piecesCounts.get(pieceId) == 0 )
                                    game.piecesPlayers.put(pieceId, 0);
                            }

                            if ( move.startsWith("r") ) {
                                game.piecesCounts.put(pieceId, game.piecesCounts.get(pieceId) - 1);
                                if ( game.piecesCounts.get(pieceId) == 0) {
                                    var plID = game.piecesPlayers.get(pieceId);
                                    game.piecesPlayers.put(pieceId, 0);

                                    if ( plID == game.p1 )
                                        game.p1Graveyard++;
                                    else
                                        game.p2Graveyard++;
                                }
                            }

                            if ( move.startsWith("t") ) {
                                game.piecesCounts.put(pieceId, game.piecesCounts.get(pieceId) + 1);
                                game.piecesPlayers.put(pieceId, uID);
                            }
                        }

                        game.requiresRefresh = true;
                    }
                }

                game.turnOf = (game.turnOf == game.p1 ? game.p2 : game.p1);
            }
        }

        return Codes.serverSuccess;
    }

    int treatRequest (int request) {
        if ( request == Codes.clientConnect ) {
            try {
                this.outParams.put ( "userID", "" + registerUsername(this.inParams.get("user")));
                return Codes.serverSuccess;
            } catch (NoUserSlot e) {
                return Codes.serverNoSlots;
            } catch (UsernameTaken e) {
                return Codes.serverUserTaken;
            }
        }

        if ( request == Codes.clientDisconnect ) {
            unregisterUsername(Integer.parseInt(this.inParams.get("userID")));
            return Codes.serverSuccess;
        }

        if ( request == Codes.clientInMatchmaking ) {
            return this.treatMatchmaking();
        }

        if ( request == Codes.clientInGameRefresh ) {
            return this.treatInGame ();
        }

        if ( request == Codes.clientRolled ) {
            return this.treatRoll ();
        }

        if ( request == Codes.clientMoved ) {
            return this.clientMoved ();
        }

        return Codes.serverResponseUnknownRequest;
    }

    int treatRoll() {
        var uID = Integer.parseInt(this.inParams.get("userID"));
        var match = getMatchForUID(uID);

        synchronized (match.lock) {
            if ( match.state.equals(Match.State.CHOOSING_STARTER) ) {
                match.p1FirstRoll[0] = Integer.parseInt(inParams.get("diceRoll").split("%")[0]);
                match.p1FirstRoll[1] = Integer.parseInt(inParams.get("diceRoll").split("%")[1]);

                match.state = Match.State.CHOOSING_STARTER_FIRST_ROLLED;

                match.turnOf = match.p2;
            } else if ( match.state.equals(Match.State.CHOOSING_STARTER_FIRST_ROLLED) ) {
                match.p2FirstRoll[0] = Integer.parseInt(inParams.get("diceRoll").split("%")[0]);
                match.p2FirstRoll[1] = Integer.parseInt(inParams.get("diceRoll").split("%")[1]);

                outParams.put("otherRoll", match.p1FirstRoll[0] + "%" + match.p1FirstRoll[1]);

                match.state = Match.State.CHOOSING_STARTER_SECOND_ROLLED;
            }

            BiFunction<Integer, Integer, Integer> dicePower = (a, b) -> { if ( a.equals(b) ) return 2 * (a + b); return a + b; };

            if ( match.state.equals(Match.State.CHOOSING_STARTER_SECOND_ROLLED) ) {
                var rollP1 = dicePower.apply(match.p1FirstRoll[0], match.p1FirstRoll[1]);
                var rollP2 = dicePower.apply(match.p2FirstRoll[0], match.p2FirstRoll[1]);
                if (rollP1.equals(rollP2)) {
                    match.turnOf = match.p1;
                    match.state = Match.State.CHOOSING_STARTER;

                    outParams.put("message", "Rolls were the same for both players");
                    return Codes.serverResponseError;
                } else {
                    if (rollP1.compareTo(rollP2) > 0) {
                        match.turnOf = match.p1;
                    } else {
                        match.turnOf = match.p2;
                    }

                    match.state = Match.State.GAME_RUNNING;
                    return Codes.serverMakeMove;
                }
            } else if ( match.state.equals(Match.State.GAME_RUNNING) ) {
                if ( match.turnOf == match.p1 ) {
                    match.p1FirstRoll[0] = Integer.parseInt(inParams.get("diceRoll").split("%")[0]);
                    match.p1FirstRoll[1] = Integer.parseInt(inParams.get("diceRoll").split("%")[1]);
                } else if ( match.turnOf == match.p2 ) {
                    match.p2FirstRoll[0] = Integer.parseInt(inParams.get("diceRoll").split("%")[0]);
                    match.p2FirstRoll[1] = Integer.parseInt(inParams.get("diceRoll").split("%")[1]);
                }

                return Codes.serverMakeMove;
            }
        }

        return Codes.serverSuccess;
    }
}
