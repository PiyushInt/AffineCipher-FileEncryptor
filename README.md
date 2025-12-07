# AffineCipher-FileEncryptor

## Overview

AffineCipher-FileEncryptor is a desktop application built with Java and Swing that allows users to securely encrypt and decrypt files using the Affine Cipher algorithm. The tool features a simple graphical user interface for easy file selection, encryption, and decryption.

## Features

- **Affine Cipher Encryption/Decryption:** Utilizes the Affine Cipher for secure file content transformation.
- **User-Friendly GUI:** Built with Java Swing, enabling intuitive file selection and operation.
- **Automated Key Generation:** Randomly generates valid keys for each encryption session.
- **Robust Error Handling:** Ensures secure and accurate encryption/decryption with comprehensive error messages and key validation.
- **Clear User Feedback:** Provides real-time status updates and guidance throughout the process.

## How It Works

1. **Select File:** Choose the file you want to encrypt using the GUI.
2. **Encrypt:** The application encrypts the file content with randomly generated keys and saves it as a `.enc` file.
3. **Decrypt:** Decrypt the `.enc` file to restore the original content, saved as a `.dec` file.
4. **Close:** Exit the application when finished.

## Technologies Used

- Java
- Java Swing (GUI)

## Resume Description Example

> FILE ENCRYPTION & DECRYPTION | Java, Swing  
> Developed a desktop application for file encryption and decryption using the Affine Cipher algorithm.  
> Implemented a user-friendly GUI with Java Swing, allowing users to select files, encrypt, and decrypt them with ease.  
> Added robust error handling and key validation to ensure secure and accurate encryption/decryption.  
> Automated key generation and provided clear user feedback for all operations, ensuring a smooth user experience.

## Getting Started

1. Clone or download this repository.
2. Compile the Java file:
   ```sh
   javac AffineCipherTool.java
   ```
3. Run the application:
   ```sh
   java AffineCipherTool
   ```

## License

This project is licensed under the MIT License.
