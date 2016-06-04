package userinterface;

public class ScreenInformation {

	private static final double DEFAULT_WIDTH = 800;
	private static final double DEFAULT_HEIGHT = 600;
	private static final double DEFAULT_ASPECT_RATIO = 3 / 4;

	private double _width;
	private double _height;
	private boolean _isFullScreen;

	public ScreenInformation(double _width, double _height, boolean _isFullScreen) {
		super();
		this._width = _width;
		this._height = _height;
		this._isFullScreen = _isFullScreen;
	}

	public double get_width() {
		return _width;
	}

	public void set_width(double _width) {
		this._width = _width;
	}

	public double get_height() {
		return _height;
	}

	public void set_height(double _height) {
		this._height = _height;
	}

	public boolean is_isFullScreen() {
		return _isFullScreen;
	}

	public void set_isFullScreen(boolean _isFullScreen) {
		this._isFullScreen = _isFullScreen;
	}

}
