package app.communication;

import app.client.Board;

import java.awt.*;

public interface RollRequestCallback {
    void placedAt (Rectangle r, Board.PieceType type);
}
