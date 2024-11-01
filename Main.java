import java.security.InvalidKeyException;
import java.util.*;

class Ship {
    String[] possiblePositions = {"horizontal", "vertical", "across"};

    private int length = 3; // length of ship default is 3
    private String position; // vertical or horizontal

    public int getLength(){
        return this.length;
    };

    public void setLength(int len) throws InvalidKeyException {
        if(len < 0){
            throw new InvalidKeyException("Length must be more than 0!");
        };
        this.length = len;
    };

    public String getPosition(){
        return this.position;
    };

    public void setPosition(String position) throws InvalidKeyException {
        this.position = position;
    };

    public void createRandomShip() throws InvalidKeyException {
        Random rand = new Random();
        int randomPositionIndex = rand.nextInt(this.possiblePositions.length);
        this.setPosition(this.possiblePositions[randomPositionIndex]);

        int randomLength = rand.nextInt(3, 5);
        this.setLength(randomLength);
    };
};


class  Game {
    int columns;
    int rows;
    int[][] grid;

    public static void main(String[] args) throws InvalidKeyException {
        Game game = new Game();
        game.initialize();

        for(int i=0; i<game.rows; i++){
            for (int j=0; j<game.columns; j++){
                System.out.print(Integer.toString(game.grid[i][j]) + ' ');
            }
            System.out.println();
        }
    }

    private void initialize() throws InvalidKeyException {
        Scanner obj = new Scanner(System.in);
        System.out.print("Enter number of columns in grid: ");
        this.columns = obj.nextInt();

        System.out.print("Enter number of rows in grid: ");
        this.rows = obj.nextInt();

        System.out.print("Enter number of ships inside grid: ");
        int numOfShips = obj.nextInt();

        this.grid = this.makeGrid(this.columns, this.rows);

        int i = 0;
        while (i < numOfShips) {
            // make random ship
            Ship ship = this.makeShip();

            // check if it can be placed

            if(this.tryToPlaceShip(this.grid, ship)) i = i+1;
            // place it if yes

            // test another if not
        }
    }

    private int[][] makeGrid(int columns, int rows) throws InvalidKeyException {
        if (columns < 0 | rows < 0) {
            throw new InvalidKeyException("Grid ");
        }
        // initializes with 0
        return new int[rows][columns];
    }

    private Ship makeShip() throws InvalidKeyException {
        Ship newShip = new Ship();
        newShip.createRandomShip();
        return newShip;
    }

    private boolean tryToPlaceShip(int[][] grid, Ship ship) {
        Random rand = new Random();

        int x = rand.nextInt(this.columns);
        int y = rand.nextInt(this.rows);

        Dictionary<String, int[][]> sides = new Hashtable<String, int[][]>();

        // [0, 1] => [x, y]

        sides.put("horizontal", new int[][]{{1, 0}, {-1, 0}});
        sides.put("vertical", new int[][]{{0, 1}, {0, -1}});
        sides.put("across", new int[][]{{1, -1}, {1, 1}, {-1, 1}, {-1, -1}});

        // Test with sides
        String position = ship.getPosition();

        for (int i = 0; i < sides.get(position).length; i++) {
            int add_x = sides.get(position)[i][0];
            int add_y = sides.get(position)[i][1];
            boolean isPlaceable = this.findCoordinates(ship.getLength(), x, y, add_x, add_y);
            if (isPlaceable) {
                placeShip(ship.getLength(), x, y, add_x, add_y);
                return true;
            }
        }
        return false;
    }

    private boolean findCoordinates(int len, int x, int y, int add_x, int add_y) {
        int check_x;
        int check_y;
        int i = 0;
        while (i < len) {
            check_x = x + i * (add_x);
            check_y = y + i * (add_y);
            if (check_y >= this.rows | check_y < 0) return false;
            if (check_x >= this.columns | check_x < 0) return false;
            if (this.grid[check_y][check_x] != 0) return false;
            i = i + 1;
        }
        return true;
    }

    private void placeShip(int len, int x, int y, int add_x, int add_y){
        for(int i=0; i< len; i++){
            int x_index = x + i * add_x;
            int y_index = y + i * add_y;
            grid[y_index][x_index] = i+1;
        }
    };
}