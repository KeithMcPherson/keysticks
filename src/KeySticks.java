/*
 * This is really ugly code, and I'm sorry for everyone who has to read it.
 * I wrote it quickly and didn't take the time to comment/document it, but it's pretty self explanatory.
 * I'll clean it up later.
 * You'll need to add the processing core library and procontroll library, I'll write a readme later.
 */


import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.SliderUI;

import processing.core.PApplet;
import processing.core.PImage;
import procontroll.*;

public class KeySticks extends PApplet {
	final float CORNER = .5f;

	
	private ControllIO controll;
	private ControllDevice device;
	private ControllSlider leftSliderX;
	private ControllSlider leftSliderY;
	private ControllSlider rightSliderX;
	private ControllSlider rightSliderY;
	private ControllButton backspace;
	
	PImage leftStick;
	PImage topSelect;
	PImage rightSelect;
	PImage bottomSelect;
	PImage leftSelect;
	
	PImage rightStick;
	PImage rightSelectTop;
	PImage rightSelectTopRight;
	PImage rightSelectRight;
	PImage rightSelectBottomRight;
	PImage rightSelectBottom;
	PImage rightSelectBottomLeft;
	PImage rightSelectLeft;
	PImage rightSelectTopLeft;

	PImage atoh;
	PImage itop;
	PImage qtox;
	PImage sym;

	char[] atohChars = {'a','b','c','d','e','f','g','h'};
	char[] itopChars = {'i','j','k','l','m','n','o','p'};
	char[] qtoxChars = {'q','r','s','t','u','v','w','x'};
	char[] symChars  = {'y','z','.',',','?','!','_',' '};
	char[] emptyChars= {' ',' ',' ',' ',' ',' ',' ',' '};

	String message = "";

	char[] selectedChars = emptyChars;
	char selected = '\0';
	float lastBackspace = 0.0f;
	public void setup(){
		controll = ControllIO.getInstance(this);
		device = controll.getDevice("Controller (XBOX 360 For Windows)");
		device.setTolerance(0.01f);
		
		leftSliderX = device.getSlider(1);
		leftSliderY = device.getSlider(0);
  
  
		rightSliderX = device.getSlider(3);
		rightSliderY = device.getSlider(2);
		
		backspace = device.getButton(9);
		
		size(400,400);
		leftStick = loadImage("img/leftstick.png");
		topSelect = loadImage("img/leftsticktop.png");
		rightSelect = loadImage("img/leftstickright.png");
		bottomSelect = loadImage("img/leftstickbottom.png");
		leftSelect = loadImage("img/leftstickleft.png");

		rightStick = loadImage("img/rightstick.png");         
		rightSelectTop = loadImage("img/rightsticktop.png");     
		rightSelectTopRight = loadImage("img/rightsticktopright.png");
		rightSelectRight = loadImage("img/rightstickright.png");   
		rightSelectBottomRight = loadImage("img/rightstickbottomright.png");
		rightSelectBottom = loadImage("img/rightstickbottom.png");  
		rightSelectBottomLeft = loadImage("img/rightstickbottomleft.png");
		rightSelectLeft = loadImage("img/rightstickleft.png");    
		rightSelectTopLeft = loadImage("img/rightsticktopleft.png"); 
		
		atoh = loadImage("img/atoh.png"); 
		itop = loadImage("img/itop.png"); 
		qtox = loadImage("img/qtox.png");
		sym = loadImage("img/sym.png");
	}
	
	public void draw() {
		background(255);
		/* Left Stick */
		tint(255, 255);
		image(leftStick, 0,0);
		if (leftSliderY.getValue() < -.9) {
			tint(255, 117);
			image(topSelect, 0,0);
			tint(255, 255);
			image(atoh, 100,0);
			selectedChars = atohChars;
		} else if (leftSliderY.getValue() > .9) {
			tint(255, 117);
			image(bottomSelect, 0,0);
			tint(255, 255);
			image(qtox, 100,0);
			selectedChars = qtoxChars;
		} else if (leftSliderX.getValue() > .9) {
			tint(255, 117);
			image(rightSelect, 0,0);
			tint(255, 255);
			image(itop, 100,0);
			selectedChars = itopChars;
		} else if (leftSliderX.getValue() < -.9) {
			tint(255, 117);
			image(leftSelect, 0,0);
			tint(255, 255);
			image(sym, 100,0);
			selectedChars = symChars;
		} else {
			tint(255, 255);
			image(rightStick, 100,0);
			selectedChars = emptyChars;
		}
		
		
		/* Right Stick */
		tint(255, 117);
		if (rightSliderY.getValue() < -CORNER && rightSliderX.getValue() > CORNER){
			image(rightSelectTopRight, 100,0);
			selected = selectedChars[1];
		} else if (rightSliderY.getValue() > CORNER && rightSliderX.getValue() > CORNER){
			image(rightSelectBottomRight, 100,0);
			selected = selectedChars[3];
		} else if (rightSliderY.getValue() < -CORNER && rightSliderX.getValue() < -CORNER){
			image(rightSelectTopLeft, 100,0);
			selected = selectedChars[7];
		} else if (rightSliderY.getValue() > CORNER && rightSliderX.getValue() < -CORNER){
			image(rightSelectBottomLeft, 100,0);
			selected = selectedChars[5];
		} else if (rightSliderY.getValue() < -.9) {
			image(rightSelectTop, 100,0);
			selected = selectedChars[0];
		} else if (rightSliderY.getValue() > .9) {
			image(rightSelectBottom, 100,0);
			selected = selectedChars[4];
		} else if (rightSliderX.getValue() > .9) {
			image(rightSelectRight, 100,0);
			selected = selectedChars[2];
		} else if (rightSliderX.getValue() < -.9) {
			image(rightSelectLeft, 100,0);
			selected = selectedChars[6];
		} else {
			if (selected != '\0'){
				message += selected;
				selected = '\0';
			}
		}
		
		if(lastBackspace != backspace.getValue() && backspace.getValue() == 8.0f && message.length()>0){
			message = message.substring(0, message.length()-1);
		}
		
		lastBackspace = backspace.getValue();
		
		fill(0);
		text(message, 10, 150);
		
	}
}
