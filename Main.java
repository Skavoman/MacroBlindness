import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Main {
    static Action sGA;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                casg();
            }
        });
    }
    public static void casg() {
        JFrame fr = new JFrame("MacroBlindness");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setSize(400, 400);
        fr.setResizable(false);
        GridPanel panel = new GridPanel(16, 16, 8, "p_map.parr");
        panel.setLayout(new FlowLayout());
        fr.add(panel);
        fr.pack();
        fr.setVisible(true);
        panel.requestFocusInWindow();
    }
}

class GridPanel extends JPanel {
    private int rows;
    private int cols;
    private int cellSize;
    Color[][] gridColors;
    private int[][] grid = new int[16][16];


    public GridPanel(int rows, int cols, int cellSize, String filename) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;
        this.gridColors = new Color[rows][cols];
        
        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));

        Action sGA;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gridColors[i][j] = Color.WHITE;
            }
        }
        setFocusable(true);
        
        gridColors[1][1] = Color.BLACK;
        
        //read from pixel array file
        Timer timer;
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadGridFromFile(filename);
                repaint();
            }
        });
        timer.start();
        
    }
    private void loadGridFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            for (int row = 0; row < 16; row++) {
                String line = br.readLine();
                for (int col = 0; col < 16; col++) {
                    if(line == null){line = "0000000000000000";}
                    grid[row][col] = Character.getNumericValue(line.charAt(col));
                    if(line == null){line = "0000000000000000";}
                }
                
            }
        } catch (IOException e) {}
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                } else if (grid[i][j] != 0) {
                    g.setColor(Color.BLACK);
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                } else {
                    g.setColor(gridColors[i][j]);
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }
    }
}

