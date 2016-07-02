package userinterface;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class PanelSetFace {

	private HBox masterBox;
	private Label nameLabel;
	private Label scoreLabel;
	private ImageView profileImage;

	public PanelSetFace() {

	}

	public PanelSetFace(HBox masterBox, Label nameLabel, Label scoreLabel, ImageView profileImage) {
		super();
		this.masterBox = masterBox;
		this.nameLabel = nameLabel;
		this.scoreLabel = scoreLabel;
		this.profileImage = profileImage;
	}

	public HBox getMasterBox() {
		return masterBox;
	}

	public void setMasterBox(HBox masterBox) {
		this.masterBox = masterBox;
	}

	public Label getNameLabel() {
		return nameLabel;
	}

	public void setNameLabel(Label nameLabel) {
		this.nameLabel = nameLabel;
	}

	public ImageView getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(ImageView profileImage) {
		this.profileImage = profileImage;
	}

	public void updateScore(String score) {
		scoreLabel.setText(score);
	}
	
	public void changeToDC(){
		masterBox.setStyle("-fx-background-color:rgba(255,0,0,1)");
	}
	
	public void changeToConnected(){
		masterBox.setStyle("-fx-background-color:rgba(0,0,0,0.3)");
	}

}
