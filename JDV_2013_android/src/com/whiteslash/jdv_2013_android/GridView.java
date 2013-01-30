package com.whiteslash.jdv_2013_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GridView extends View implements View.OnTouchListener{
    
    public static final int PAUSE = 0;
    public static final int RUNNING = 1;
    
    private Life _life;
    private Context _context;
    
    private long _moveDelay = 250;
    
    private RefreshHandler _redrawHandler = new RefreshHandler();
	private int displayHeight;
	private int displayWidth;

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        _context=context;
        initGridView();
        this.setOnTouchListener(this);
    }
    
    public void setMode(int mode) {
        if (mode == RUNNING) {
            update();
            return;
        }
        if (mode == PAUSE) {
            // TODO: implement.
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.background));

        Paint cell = new Paint();
        cell.setColor(getResources().getColor(R.color.cell));

        // draw background
        canvas.drawRect(0, 0, displayWidth, displayHeight, background);

        Color cellColor=new Color();
        // draw cells
        for (int h = 0; h < Life.HEIGHT; h++) {
            for (int w = 0; w < Life.WIDTH; w++) {
                if (Life.getGrid()[h][w] != 0) {
                	cell.setColor(Color.rgb(230, Math.max(295-Life.getGrid()[h][w]*100, 0), 50));
                    canvas.drawRect(
                        w * Life.CELL_SIZE, 
                        h * Life.CELL_SIZE, 
                        (w * Life.CELL_SIZE) + (Life.CELL_SIZE -1),
                        (h * Life.CELL_SIZE) + (Life.CELL_SIZE -1),
                        cell);
                }
            }
        }
    }
    
    private void update() {
        _life.generateNextGeneration();
        int speedLevel=Integer.parseInt(PreferencesActivity
                .getAnimationSpeed(this._context));
        switch (speedLevel) {
		case 1:
			_moveDelay=50;
			break;
		case 2:
			_moveDelay=100;
			break;
		case 3:
			_moveDelay=250;
			break;
		case 4:
			_moveDelay=750;
			break;
		case 5:
			_moveDelay=2000;
			break;

		default:
			_moveDelay=250;
			break;
		}
        _redrawHandler.sleep(_moveDelay);
    }
    
    private void initGridView() {
        setFocusable(true);
    }
    
    
    class RefreshHandler extends Handler {

        @Override
        public void handleMessage(Message message) {
           GridView.this.update();
            GridView.this.invalidate();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }


	public void setWidth(int width) {
		this.displayWidth=width;
	}

	public void setHeight(int height) {
		this.displayHeight=height;		
	};
	
	public void initLife(){
		//Log.i("white", Integer.toString(displayWidth));
		//Log.i("white", Integer.toString(displayHeight));
		_life = new Life(_context, displayWidth, displayHeight);
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		//Log.i("white", "X touché : " + arg1.getX());
		//Log.i("white", "X touché : " + arg1.getY());
		_life.createCell(arg1.getX(), arg1.getY());
		this.invalidate();
		return true;
	}
    
}