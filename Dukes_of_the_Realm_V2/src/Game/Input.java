package Game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import static javafx.scene.input.KeyCode.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Input {

	/**
	 * Bitset which registers if any {@link KeyCode} keeps being pressed or if it is
	 * released.
	 */
	private BitSet keyboardBitSet = new BitSet();

	private Scene scene = null;

	public Input(Scene scene) {
		this.scene = scene;
	}

	public void addListeners() {
		scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
		scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
	}

	public void removeListeners() {
		scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
		scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);
	}

	/**
	 * "Key Pressed" handler for all input events: register pressed key in the
	 * bitset
	 */
	private EventHandler<KeyEvent> keyPressedEventHandler = event -> {
		// register key down
		keyboardBitSet.set(event.getCode().ordinal(), true);
		event.consume();
	};

	/**
	 * "Key Released" handler for all input events: unregister released key in the
	 * bitset
	 */
	private EventHandler<KeyEvent> keyReleasedEventHandler = new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
			// register key up
			keyboardBitSet.set(event.getCode().ordinal(), false);
			event.consume();
		}
	};

	private boolean is(KeyCode key) {
		return keyboardBitSet.get(key.ordinal());
	}

	// -------------------------------------------------
	// Evaluate bitset of pressed keys and return the player input.
	// If direction and its opposite direction are pressed simultaneously, then the
	// direction isn't handled.
	// -------------------------------------------------

	public void sauvegardeGame() throws Exception{
		
		File fichierGame = new File("saveGameFile.txt");
		if (fichierGame == null){
			System.out.println("The file was Exist or Empty");
		}
		if (fichierGame.exists()){
			fichierGame.delete();
		}
		String str = "Teste";
		
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream outputStream = null;
		
		try {
			fileOutputStream = new FileOutputStream(fichierGame);
			outputStream = new ObjectOutputStream(fileOutputStream);
			
			outputStream.writeUTF(str);
			outputStream.close();
			//byte[] strToBytes = str.getBytes();
		    //outputStream.write(strToBytes);
			//outputStream.writeInt(1500);;
			
			//Ici possedé aux enregistrement des chateaux
		}
		catch (IOException exeception) {
			exeception.printStackTrace();
		}
		finally {
			try {
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					if (outputStream != null) {
						outputStream.close();
					}
			}
			catch (IOException e) {
				//plus tard utiliser Game_Exception pour géré les putains d'exceptions
				e.printStackTrace();
			}
		}
	}
	
	public void ouvrirGame(File file) throws Exception {
		
		if ((file == null) || (file.length() == 0) || !(file.exists())) {
				//throw new Game_Exception("The file was Exist or Empty");
				//System.out.println("The file was Exist or Empty");
				//Afficher une fenetre pour le message
			Alert dialogW = new Alert(AlertType.WARNING);
			dialogW.setTitle("Upload Game");
			dialogW.setHeaderText(null); // No header
			dialogW.setContentText("The file was Exist or Empty !");
			dialogW.showAndWait();
		}
		
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		
		try {
			fileInputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(fileInputStream);
			
			//lire
			Object chene = objectInputStream.readObject();
			System.out.println(chene);
			
			
			
		}catch (IOException e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
				if (objectInputStream != null) {
					objectInputStream.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	public boolean isExit() {
		return is(ESCAPE);
	}
	public boolean isPause()
	{
		return is(P);
	}
}
