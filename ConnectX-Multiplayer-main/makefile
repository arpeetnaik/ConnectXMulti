default: cpsc2150/extendedConnectX/models/AbsGameBoard.java cpsc2150/extendedConnectX/models/BoardPosition.java cpsc2150/extendedConnectX/models/GameBoard.java cpsc2150/extendedConnectX/models/GameScreen.java cpsc2150/extendedConnectX/models/IGameBoard.java cpsc2150/extendedConnectX/models/GameBoardMem.java
	javac cpsc2150/extendedConnectX/models/AbsGameBoard.java cpsc2150/extendedConnectX/models/BoardPosition.java cpsc2150/extendedConnectX/models/GameBoard.java cpsc2150/extendedConnectX/models/GameScreen.java cpsc2150/extendedConnectX/models/IGameBoard.java cpsc2150/extendedConnectX/models/GameBoardMem.java

run: cpsc2150/extendedConnectX/models/AbsGameBoard.class cpsc2150/extendedConnectX/models/BoardPosition.class cpsc2150/extendedConnectX/models/GameBoard.class cpsc2150/extendedConnectX/models/GameScreen.class cpsc2150/extendedConnectX/models/IGameBoard.class cpsc2150/extendedConnectX/models/GameBoardMem.class
	java cpsc2150.extendedConnectX.models.GameScreen

test: cpsc2150/extendedConnectX/models/*.java
	javac -cp .:/usr/share/java/junit4.jar cpsc2150/extendedConnectX/models/*.java

testGB: cpsc2150/extendedConnectX/models/AbsGameBoard.class cpsc2150/extendedConnectX/models/BoardPosition.class cpsc2150/extendedConnectX/models/GameBoard.class cpsc2150/extendedConnectX/models/GameScreen.class cpsc2150/extendedConnectX/models/IGameBoard.class cpsc2150/extendedConnectX/models/GameBoardMem.class cpsc2150/extendedConnectX/models/TestGameBoard.java
	java -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore cpsc2150.extendedConnectX.models.TestGameBoard

testGBmem: cpsc2150/extendedConnectX/models/AbsGameBoard.class cpsc2150/extendedConnectX/models/BoardPosition.class cpsc2150/extendedConnectX/models/GameBoard.class cpsc2150/extendedConnectX/models/GameScreen.class cpsc2150/extendedConnectX/models/IGameBoard.class cpsc2150/extendedConnectX/models/GameBoardMem.class cpsc2150/extendedConnectX/models/TestGameBoardMem.java
	java -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore cpsc2150.extendedConnectX.models.TestGameBoardMem

clean: 
	rm -f cpsc2150/extendedConnectX/models/*.class