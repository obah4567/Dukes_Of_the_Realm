package Game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.BitSet;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import static javafx.scene.input.KeyCode.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Input {
	
	//private Castle castle;

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

	//private ArrayList<Castle> chateau = new ArrayList<Castle>();
	
	/*
	 * This function save in file the game, for this we used file with .txt
	 * we declared a fileOutputStream for open the "file.txt" 
	 * An ObjectOutputStram class is used to store the primitive data type
	 * and graph of Java object to an output stream, whereas an 
	 * Serializable interface are written to stream.
	 */
	
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
			
			/*
			 * Castle chatPLay = new Castle(castle.getImage(), castle.getLayer(), "Joueur");
			 * //Castle c = new Castle(castle.getImgView(), castle.getLayer(), "Joueur");
			 * Player player = new Player(chatPLay); chateau.add(chatPLay);
			 */
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
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Open the file save for generate a new game, 
	 * if file not exist, you have window with message warning.
	 */
	
	public void ouvrirGame(File file) throws Exception {
		
		if ((file == null) || (file.length() == 0) || !(file.exists())) {
			//Show window with message for warning
			Alert dialogW = new Alert(AlertType.WARNING);
			dialogW.setTitle("Upload Game");
			dialogW.setHeaderText(null);
			dialogW.setContentText("The file was Exist or Empty, please go try a new game!");
			dialogW.showAndWait();
		}
		
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		
		try {
			fileInputStream = new FileInputStream(file);
			objectInputStream = new ObjectInputStream(fileInputStream);
			
			//lire ce qui est ecrit dans le fichier passer en paramêtre,
			//genere grace à la sauvergarde
			Object chene = objectInputStream.readObject();
			System.out.println(chene);
			
			
			
		}catch (IOException e) {
			e.printStackTrace();
			
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