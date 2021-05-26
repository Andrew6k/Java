package app.client;

import app.communication.Callback;
import app.communication.RollRequestCallback;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Board {
    private Rectangle left, right;

    public enum PieceType {
        NONE,
        WHITE,
        BLACK
    }

    PieceType playingAs = PieceType.NONE;

    public PieceType getPlayingAs() {
        return playingAs;
    }

    Map < Integer, PieceType > pieceTypeMap = new HashMap<>();
    Map < Integer, Integer > pieceCountMap  = new HashMap<>();
    Map < Integer, Rectangle > piecePositionMap = new HashMap<>();

    public Map<Integer, Integer> getPieceCountMap() {
        return pieceCountMap;
    }

    public Map<Integer, PieceType> getPieceTypeMap() {
        return pieceTypeMap;
    }

    public Map<Integer, Rectangle> getPiecePositionMap() {
        return piecePositionMap;
    }

    public Board () {
        this.playingAs = PieceType.WHITE; // temp

        for (int i = 0; i < 24; i++) {
            pieceTypeMap.put(i, PieceType.NONE);
            pieceCountMap.put(i, 0);
        }
    }

    private int whiteInGraveyard = 0;
    private int blackInGraveyard = 0;

    public int getWhiteInGraveyard() {
        return whiteInGraveyard;
    }

    public int getBlackInGraveyard() {
        return blackInGraveyard;
    }

    public void setWhiteInGraveyard(int whiteInGraveyard) {
        this.whiteInGraveyard = whiteInGraveyard;
    }

    public void setBlackInGraveyard(int blackInGraveyard) {
        this.blackInGraveyard = blackInGraveyard;
    }

    public void passNewSizes(Rectangle left, Rectangle right) {
        this.left = left;
        this.right = right;

        this.piecePositionMap.clear();

        if ( this.playingAs.equals(PieceType.WHITE) ) {
            int d = right.width / 6;
            for (int i = 0; i < 6; i++) {
                piecePositionMap.put(i, new Rectangle(right.x + right.width - (i+1) * d, right.y + right.height - right.height / 3, d, right.height / 3));
                piecePositionMap.put(i + 6, new Rectangle(left.x + left.width - (i + 1) * d, left.y + left.height - left.height / 3, d, left.height / 3 ));
                piecePositionMap.put(i + 12, new Rectangle(left.x + i * d, left.y, d, left.height / 3));
                piecePositionMap.put(i + 18, new Rectangle(right.x + i * d, right.y, d, right.height / 3));
            }

            piecePositionMap.forEach((k, v) -> System.out.println(k + " : " + v.toString()));
        } else {
            int d = right.width / 6;
            for (int i = 0; i < 6; i++) {
                piecePositionMap.put(i, new Rectangle(left.x + i * d, left.y + left.height - left.height / 3, d, left.height / 3));
                piecePositionMap.put(i + 6, new Rectangle(right.x + i * d, right.y + right.height - right.height / 3, d, right.height / 3));
                piecePositionMap.put(i + 12, new Rectangle(right.x + right.width - (i + 1) * d, right.y, d, right.height / 3));
                piecePositionMap.put(i + 18, new Rectangle(left.x + left.width - (i + 1) * d, left.y, d, left.height / 3));
            }
        }
    }

    private Callback placeCallback = null;

    public void setPlaceCallback(Callback placeCallback) {
        this.placeCallback = placeCallback;
    }

    public void addPieceAt (int contIndex) {
//        this.pieceCountMap.
//        System.out.println("Add at " + contIndex);
        this.pieceTypeMap.put(contIndex, this.playingAs);
        this.pieceCountMap.put(contIndex, this.pieceCountMap.get(contIndex) + 1);
//        if (this.placeCallback != null) {
//            for ( var i = 0; i < 24; i++ )
//                this.placeCallback.placedAt(this.piecePositionMap.get(contIndex), this.pieceTypeMap.get(contIndex), this.pieceCountMap.get(contIndex));
//        }
    }

    boolean isTop, isAtDice, isBottom;
    boolean isLeft, isRight;
    int locIndexRelative, locIndexAbsolute;

    RollRequestCallback rollRequestCallback;

    public void setRollRequestCallback(RollRequestCallback rollRequestCallback) {
        this.rollRequestCallback = rollRequestCallback;
    }

    AtomicBoolean rollingEnabled = new AtomicBoolean(false);
    AtomicBoolean movingEnabled = new AtomicBoolean(false);
    AtomicBoolean replaceEnabled = new AtomicBoolean(false);

    public void setRollingEnabled(boolean toggle) {
        this.rollingEnabled.set(toggle);
    }

    public void setMovingEnabled (boolean toggle) {
        this.movingEnabled.set(toggle);
    }

    public boolean isRollingEnabled () {
        return this.rollingEnabled.get();
    }


    public void setReplaceEnabled (boolean toggle) {
        this.replaceEnabled.set(toggle);
    }

    public boolean isReplaceEnabled () {
        return this.replaceEnabled.get();
    }

    public boolean isMovingEnabled () {
        return this.movingEnabled.get();
    }

    private int polledPos = -1;

    public int getPolledPos() {
        return polledPos;
    }

    public void mouseClicked (MouseEvent e) {
        if (!isLeft && !isRight) return;
        if (!isTop && !isAtDice && !isBottom) return;

        if ( e.getButton() == MouseEvent.BUTTON1 ) {
            this.polledPos = this.locIndexAbsolute;
            this.placeCallback.f();

//            if (locIndexRelative != -1) this.addPieceAt(locIndexAbsolute);
            if ( rollingEnabled.get() ) {
                if (isRight && isAtDice && rollRequestCallback != null)
                    rollRequestCallback.placedAt(new Rectangle(right.x, right.y + right.height / 3, right.width, right.height / 3), playingAs);
                else if (isLeft && isAtDice && rollRequestCallback != null)
                    rollRequestCallback.placedAt(new Rectangle(left.x, left.y + left.height / 3, left.width, left.height / 3), playingAs);
            }
        }
    }

    public void setPlayingAs(PieceType playingAs) {
        this.playingAs = playingAs;
    }

    public void mouseMoved (MouseEvent e) {
        Rectangle side = null;

        isTop = false;
        isAtDice = false;
        isBottom = false;

        isLeft = false;
        isRight = false;
        locIndexAbsolute = -1;
        locIndexRelative = -1;

        if ( e.getX() < right.x + right.width && e.getX() >= right.x )
            side = right;
        else if ( e.getX() >= left.x && e.getX() < left.x + left.width )
            side = left;

        if (side == null) return;

        isLeft = side == left;
        isRight = ! isLeft;

        isTop = false;
        isAtDice = false;
        isBottom = false;

        if ( e.getY() < side.y + side.height / 3 && e.getY() >= side.y )
            isTop = true;
        else if ( e.getY() >= side.y + side.height / 3 && e.getY() < side.y + side.height / 3 * 2 )
            isAtDice = true;
        else if ( e.getY() >= side.y + side.height / 3 * 2 && e.getY() < side.y + side.height )
            isBottom = true;

        if ( ! isTop && ! isAtDice && ! isBottom ) return;

        int contIndex = -1;
        if ( ! isAtDice ) {
            int d = side.width / 6;
            for ( int i = 0; i < 6; i++ )
                if ( side.x + d * (i+1) > e.getX() && side.x + d * i <= e.getX() ) {
                    contIndex = i;
                    break;
                }
        }

        int locIndex = 0;
        if ( this.playingAs.equals(PieceType.WHITE) ) {
            locIndex = 5 - contIndex;
            if (isBottom && side == left) locIndex += 6;
            else if (isTop && side == left) locIndex = 5 - locIndex + 12;
            else if (isTop && side == right) locIndex = 5 - locIndex + 18;
        } else if ( this.playingAs.equals(PieceType.BLACK) ) {
            locIndex = contIndex;
            if ( isBottom && isRight ) locIndex += 6;
            else if (isTop && isRight) locIndex = 5 - locIndex + 12;
            else if (isTop && isLeft) locIndex = 5 - locIndex + 18;
        }

        locIndexAbsolute = locIndex;
        locIndexRelative = contIndex;

//        var sideText = ((side == left) ? "left" : "right");
//        var locText = ( isAtDice ) ? "at dice" : ( isTop ? "top" : "bottom" );

//        var locIText = isAtDice ? "" : "" + contIndex;
//        var locActText = isAtDice ? "" : "" + locIndex;
//        System.out.println(sideText + " " + locText + " " + locIText + ", " + locActText);
    }
}
