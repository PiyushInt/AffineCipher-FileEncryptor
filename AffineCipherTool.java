import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class AffineCipherTool {
    private static int key1;
    private static int key2;
    private static final int MOD = 26;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AffineCipherTool::createGUI);
    }

    private static void createGUI() {
        JFrame frame = new JFrame("Affine Cipher File Encryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(3, 1));

        JButton selectFileButton = new JButton("Select File");
        JButton encryptButton = new JButton("Encrypt");
        JButton decryptButton = new JButton("Decrypt");
        JButton closeButton = new JButton("Close");
        JLabel statusLabel = new JLabel("Select a file to encrypt");

        frame.add(selectFileButton);
        frame.add(encryptButton);
        frame.add(statusLabel);
        frame.add(closeButton);

        decryptButton.setVisible(false);

        final File[] selectedFile = { null };
        final String[] encryptedFilePath = { null };

        selectFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile[0] = fileChooser.getSelectedFile();
                statusLabel.setText("Selected File: " + selectedFile[0].getName());
            }
        });

        encryptButton.addActionListener(e -> {
            if (selectedFile[0] == null) {
                statusLabel.setText("Please select a file first!");
                return;
            }
            try {
                key1 = generateValidKey1();
                key2 = (int) (Math.random() * MOD);
                String content = readFile(selectedFile[0].getAbsolutePath());
                String encryptedText = encrypt(content);
                String encPath = selectedFile[0].getAbsolutePath() + ".enc";
                writeFile(encPath, encryptedText);
                encryptedFilePath[0] = encPath;
                statusLabel.setText(
                        "File Encrypted! k1=" + key1 + ", k2=" + key2 + ". Click Decrypt to generate .dec file.");
                // Switch UI to show Decrypt button
                frame.remove(encryptButton);
                frame.add(decryptButton);
                decryptButton.setVisible(true);
                frame.revalidate();
                frame.repaint();
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
            }
        });

        decryptButton.addActionListener(e -> {
            if (encryptedFilePath[0] == null) {
                statusLabel.setText("No encrypted file found!");
                return;
            }
            try {
                String encryptedText = readFile(encryptedFilePath[0]);
                String decryptedText = decrypt(encryptedText);
                writeFile(encryptedFilePath[0] + ".dec", decryptedText);
                statusLabel.setText("File Decrypted! .dec file generated. You may close the window.");
                // Show Close button
                frame.add(closeButton);
                closeButton.setVisible(true);
                frame.revalidate();
                frame.repaint();
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
            }
        });

        closeButton.addActionListener(e -> {
            System.exit(0);
        });

        frame.setVisible(true);
    }

    // Generate a valid key1 (coprime with MOD=26)
    private static int generateValidKey1() {
        int[] coprimes = { 1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25 };
        int idx = (int) (Math.random() * coprimes.length);
        return coprimes[idx];
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }

    private static void writeFile(String filePath, String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(content);
        writer.close();
    }

    public static String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        for (char character : plaintext.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                int k3 = character - base;
                int encrypted = (k3 * key1 + key2) % MOD;
                ciphertext.append((char) (encrypted + base));
            } else {
                ciphertext.append(character);
            }
        }
        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        int k1_inverse = findModInverse(key1, MOD);
        if (k1_inverse == -1) {
            return "Error: Invalid Key1! Must be coprime with 26.";
        }
        for (char character : ciphertext.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isUpperCase(character) ? 'A' : 'a';
                int k3 = character - base;
                int decrypted = ((k3 - key2 + MOD) * k1_inverse) % MOD;
                plaintext.append((char) (decrypted + base));
            } else {
                plaintext.append(character);
            }
        }
        return plaintext.toString();
    }

    private static int findModInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1;
    }
}
