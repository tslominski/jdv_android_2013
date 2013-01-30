package com.whiteslash.jdv_2013_android;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Cellule {

	private Boolean _vivant = false;
	private Integer _dureeDeVie = 0;
	private Integer _x = 0;
	private Integer _y = 0;

	public Cellule(int x, int y) {
		_x = x;
		_y = y;
	}

	public void mourir() {
		_vivant = false;
	}

	public void naitre() {
		if (_vivant == false) {
			_dureeDeVie = 0;
			_vivant = true;
		}
	}

	public void vieillir() {
		_dureeDeVie++;
	}

	public void dessiner(Canvas canvas) {
		Paint cell=new Paint();
		if(_dureeDeVie==0)
			cell.setColor(Color.rgb(54, 202, 47));//vert pâle
		else if(_dureeDeVie==1)
			cell.setColor(Color.rgb(172, 220, 29));//vert un peu jaune
		else if(_dureeDeVie==2)
			cell.setColor(Color.rgb(239, 234, 24));//jaune
		else if(_dureeDeVie==3)
			cell.setColor(Color.rgb(244, 176, 19));//jaune orange
		else if(_dureeDeVie==4)
			cell.setColor(Color.rgb(249, 115, 15));//orange
		else if(_dureeDeVie==5)
			cell.setColor(Color.rgb(251, 61, 13));//orange rouge
		else
			cell.setColor(Color.rgb(255, 9, 9));//rouge petant
		
		canvas.drawRect(
                _x * Life.CELL_SIZE, 
                _y * Life.CELL_SIZE, 
                (_x * Life.CELL_SIZE) + (Life.CELL_SIZE -1),
                (_y * Life.CELL_SIZE) + (Life.CELL_SIZE -1),
                cell);
	}

	public Boolean isVivant() {
		return _vivant;
	}

}
