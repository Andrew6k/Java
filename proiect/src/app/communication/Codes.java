package app.communication;

public class Codes {
    public static final int serverTimeout = -1;
    public static final int serverSuccess = 0;
    public static final int serverResponseUnknownRequest = 1;
    public static final int serverResponseError = 2;

    public static final int serverUserTaken = 3;
    public static final int serverNoSlots = 4;

    public static final int serverMatchmakingWaiting = 5;

    public static final int serverFoundMatch = 6;

    public static final int serverNotYourTurn = 7;
    public static final int serverRollHighest = 8;
    public static final int serverMakeMove = 9;
    public static final int serverUpdateBoard = 10;

    public static final int clientRefresh = 1;
    public static final int clientDisconnect = 2;
    public static final int clientConnect = 3;
    public static final int clientInMatchmaking = 4;

    public static final int clientInGameRefresh = 5;

    public static final int clientRolled = 6;
    public static final int clientMoved = 7;
}
