import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;



/**
 * Helper methods for GUI-related business
 * At this point in time contains only create clickable Image button :)
 * 
 * @author R. van der Heijden
 * @date 20th December 2011
 * 
 * Credits to: Whoever from Sun who made the toolbar tutorial :)
 * Seems I ripped my code from there :P
 */
public class GUITools {
	
	/**
	 * Creates a JButton with an image on it :)
	 * 
	 * @param imageName the path to the image that needs to be used
	 * @param actionCommand the <code>String</code> which e.getSource() can use to determine source of event
	 * @param toolTipText The text to display when somebody hovers his/her mouse over it
	 * @param altText the text to display when the image can't be loaded or viewed.
	 * @param fileExtension the extension of the file (jpg or png or gif) which you want to use.
	 * @return the <code>JButton</code>
	 */
	protected static JButton makeClickableImageButton(String imageName, String actionCommand, String toolTipText, String altText,
										String fileExtension) {
		//	Look for the image.
		String imgLocation = "/images/" + imageName + "." + fileExtension;
		URL imageURL = GUITools.class.getResource(imgLocation); 
		// stond Toolbar, maar gaat volgens mij om bepalen map :) -> errorprone like hell :)s

		//	Create and initialize the button.
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);

		if (imageURL != null) {                      //image found
			button.setIcon(new ImageIcon(imageURL, altText));
		} else {                                     //no image found
			button.setText(altText);
			System.err.println("Resource not found: " + imgLocation);
		}

		return button;
	}

}
