var World = {
	loaded: false,

	init: function initFn() {
		this.createOverlays();
	},

	createOverlays: function createOverlaysFn() {

	this.tracker = new AR.ClientTracker("assets/tracker.wtc", {
    			onLoaded: this.worldLoaded
    		});

    var imgOne = new AR.ImageResource("assets/imgOne.png");
    		var overlayOne = new AR.ImageDrawable(imgOne, 1, {
    			offsetX: -0.15,
    			offsetY: 0,
    		});

    var pageOne = new AR.Trackable2DObject(this.tracker, "*", {
			drawables: {
				cam: overlayOne
			}
		});

	},

	worldLoaded: function worldLoadedFn() {
		World.loaded = true;
		$("#loadingMessage").remove();
	}

};

World.init();
