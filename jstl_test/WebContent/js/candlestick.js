
var extendClass = Highcharts.extendClass;
var extend = Highcharts.extend;
var pick = Highcharts.pick;
var merge = Highcharts.merge;
var each = Highcharts.each;
var mathMin = Math.min;
var mathMax = Math.max;
var mathAbs = Math.abs;
var Point = Highcharts.Point;
var defaultPlotOptions = Highcharts.getOptions().plotOptions;
var defaultSeriesOptions = defaultPlotOptions.line;
var Series = Highcharts.Series;
var Color = Highcharts.Color;
var seriesTypes = Highcharts.seriesTypes;
var NORMAL_STATE = '';
var SELECT_STATE = 'select';
var HOVER_STATE = 'hover';
var UNDEFINED = undefined;
var SVG_NS = 'http://www.w3.org/2000/svg';
var	hasSVG = !!document.createElementNS && !!document.createElementNS(SVG_NS, 'svg').createSVGRect;
var TRACKER_FILL = 'rgba(192,192,192,' + (hasSVG ? 0.000001 : 0.002) + ')';
var HIDDEN = 'hidden';
var	VISIBLE = 'visible';
var hasTouch = document.documentElement.ontouchstart !== UNDEFINED;

/**
 * Check for object
 * @param {Object} obj
 */
function isObject(obj) {
	return typeof obj === 'object';
}

function getMin ( num1, num2,num3,num4){
		var defaultMaxNumber = Number.MAX_VALUE;
		var defaultDiff = 0.001;
		var ret = mathMin( isNaN(num1) ? defaultMaxNumber : num1,
				isNaN(num2) ? defaultMaxNumber : num2,
				isNaN(num3) ? defaultMaxNumber : num3,
				isNaN(num4) ? defaultMaxNumber : num4);
		if( mathAbs( ret - defaultMaxNumber ) <= defaultDiff ){
			ret = null;
		}
		return ret;
}

function getMax ( num1, num2,num3,num4){
		var defaultMinNumber = Number.MIN_VALUE;
		var defaultDiff = 0.001;
		var ret = mathMax( isNaN(num1) ? defaultMinNumber : num1,
				isNaN(num2) ? defaultMinNumber : num2,
				isNaN(num3) ? defaultMinNumber : num3,
				isNaN(num4) ? defaultMinNumber : num4);
		if( mathAbs( ret - defaultMinNumber ) <= defaultDiff ){
			ret = null;
		}
		return ret;
}

var CandlePoint = extendClass(Point, {
	
	/**
	 * Apply the options containing the x and y data and possible some extra properties.
	 * This is called on point init or from point.update.
	 *
	 * @param {Object} options
	 */
	applyOptions: function (options, x) {
		var point = this,
			series = point.series,
			optionsType = typeof options;
		

		point.config = options;

		// onedimensional array input
		if (optionsType === 'number' || options === null) {
			point.open = point.close = point.low = point.high = point.y = options;
		} else if (typeof options[0] === 'number' && options.length == 2) { // two-dimentional array
			point.x = options[0];
			point.open = point.close = point.low = point.high = point.y = options[1];
		} else if ( typeof options[0] === 'number' ){ //[x,open,high,low,close]
			point.x = options[0];
			point.open = options[1];
			point.high = options[2];
			point.low = options[3];
			point.close = options[4];
			//point.y = getMin( point.open, point.high, point.low, point.close);
			point.y =(point.open + point.high+point.low+point.close)/4;
		} else if (optionsType === 'object' && typeof options.length !== 'number') { // object input
			// copy options directly to point
			extend(point, options);
			point.options = options;
			
			// This is the fastest way to detect if there are individual point dataLabels that need 
			// to be considered in drawDataLabels. These can only occur in object configs.
			if (options.dataLabels) {
				series._hasPointLabels = true;
			}
			
			if( !point.y ){
				point.y = ( point.open + point.high + point.low + point.close)/4;
			}
		} else if (typeof options[0] === 'string') { // categorized data with name in first position
			point.name = options[0];
			point.open = point.close = point.low = point.high = point.y = options[1];
		}
		
		/*
		 * If no x is set by now, get auto incremented value. All points must have an
		 * x value, however the y value can be null to create a gap in the series
		 */
		// todo: skip this? It is only used in applyOptions, in translate it should not be used
		if (point.x === UNDEFINED) {
			point.x = x === UNDEFINED ? series.autoIncrement() : x;
		}
		
		

	},
	/**
	 * Update the point with new options (typically x/y data) and optionally redraw the series.
	 *
	 * @param {Object} options Point options as defined in the series.data array
	 * @param {Boolean} redraw Whether to redraw the chart or wait for an explicit call
	 * @param {Boolean|Object} animation Whether to apply animation, and optionally animation
	 *    configuration
	 *
	 */
	update: function (options, redraw, animation) {
		var point = this,
			series = point.series,
			graphic = point.graphic,
			i,
			data = series.data,
			dataLength = data.length,
			chart = series.chart;

		redraw = pick(redraw, true);

		// fire the event with a default handler of doing the update
		point.firePointEvent('update', { options: options }, function () {

			point.applyOptions(options);

			// update visuals
			if (isObject(options)) {
				series.getAttribs();
				if (graphic) {
					graphic.attr(point.pointAttr[series.state]);
				}
			}

			// record changes in the parallel arrays
			for (i = 0; i < dataLength; i++) {
				if (data[i] === point) {
					series.xData[i] = point.x;
					//series.yData[i] = point.y;
					series.yData[i] = [point.open,point.high,point.low,point.close];
					series.options.data[i] = options;
					break;
				}
			}

			// redraw
			series.isDirty = true;
			series.isDirtyData = true;
			if (redraw) {
				chart.redraw(animation);
			}
		});
	},
	/**
	 * Set the point's state
	 * @param {String} state
	 */
	setState: function (state) {
		var point = this,
			plotX = point.plotX,
			plotY = point.plotY,
			series = point.series,
			stateOptions = series.options.states,
			markerOptions = defaultPlotOptions[series.type].marker && series.options.marker,
			normalDisabled = markerOptions && !markerOptions.enabled,
			markerStateOptions = markerOptions && markerOptions.states[state],
			stateDisabled = markerStateOptions && markerStateOptions.enabled === false,
			stateMarkerGraphic = series.stateMarkerGraphic,
			chart = series.chart,
			radius,
			pointAttr = point.pointAttr;

		state = state || NORMAL_STATE; // empty string

		if (
				// already has this state
				state === point.state ||
				// selected points don't respond to hover
				(point.selected && state !== SELECT_STATE) ||
				// series' state options is disabled
				(stateOptions[state] && stateOptions[state].enabled === false) ||
				// point marker's state options is disabled
				(state && (stateDisabled || (normalDisabled && !markerStateOptions.enabled)))

			) {
			return;
		}

		//before update state, we need to change pointAttr
		/*var tempColor = (point.open >=point.close) ? series.downColor : series.growColor;
		each([SELECT_STATE,NORMAL_STATE,HOVER_STATE], function (state){
			var tempAttr = point.pointAttr[state];
			tempAttr.fill = tempColor;
			tempAttr.stroke = tempColor;
			tempAttr["stroke-width"] = 1;
		});*/
		// apply hover styles to the existing point
		if (point.graphic) {
			radius = markerOptions && point.graphic.symbolName && pointAttr[state].r;
			point.graphic.attr(merge(
				pointAttr[state]
				
			));
		} else {   //there is no default candelstick symbol, so there will be such state change
			// if a graphic is not applied to each point in the normal state, create a shared
			// graphic for the hover state
			if (state && markerStateOptions) {
				if (!stateMarkerGraphic) {
					radius = markerStateOptions.radius;
					series.stateMarkerGraphic = stateMarkerGraphic = chart.renderer[series.symbolCanName](
						plotX,
						point.plotOpen,
						point.plotHigh,
						point.plotLow,
						point.plotClose
					)
					.attr(pointAttr[state])
					.add(series.group);
				}

				/*stateMarkerGraphic.translate(
					plotX,
					plotY
				);*/
			}

			if (stateMarkerGraphic) {
				stateMarkerGraphic[state ? 'show' : 'hide']();
			}
		}

		point.state = state;
	},
	//toYData is used in series.setData to save [o,h,l,c] to yData
	toYData : function(){
		var point = this;
		var ret = [point.open, point.high,point.low, point.close];
		return ret;
	},
	//write a method, only valid point, we will draw it
	validate: function(){
		var point = this;
		if( !point.open || !point.high || !point.low || !point.close ){ //null
			point.y = null;
			return false;
		}
		if( isNaN(point.open) || isNaN(point.high) ||isNaN(point.low) || isNaN(point.close)){//isNaN
			point.y = null;
			return false;
		}
		if( point.high < point.low || point.high < point.open || point.high < point.close || point.low > point.open || point.low > point.close){
			return false;
		}
		return true;
	}
	
});


defaultPlotOptions.candlestick = merge(defaultSeriesOptions, {
	colorByPoint: false,
	states: {
		hover: {
			enabled: false
		}
	}
});
var CandelStickSeries = extendClass(Series, {
	type: 'candlestick',
	pointClass: CandlePoint,
	valueCount : 4,
	hasGroupedData: false,
	symbolCanName: 'candlestick',
	defaultWidth: 8,
	growColor: '#123456',
	downColor: '#654321',
	
	//add my own symbol as candlestick in init
	init: function(chart, options){
		var series = this;
		
		Series.prototype.init.call(series, chart, options);
		
		var renderer = chart.renderer;
		if( !renderer[series.symbolName] ){
			//here, we must provide valid open/high/low/close value
			//renderer[ series.symbolName] = function(x,open,high,low,close){
			extend(renderer, {
			'candlestick': function(x,open,high,low,close){
				var path ;
				var defaultWidth = series.defaultWidth;
				if( open == high && high == low && low == close ){
					path = ['M', x - defaultWidth/2, open,
							'L', x + defaultWidth/2, open,
							'Z'
							];
				}else if( open == low && low == close ) { //high is bigger
					path = ['M', x - defaultWidth/2, open,
							'L', x + defaultWidth/2, open,
							'M', x, open,
							'L', x, high,
							'Z'
							];
				}else if( open == high&& high == close ) { //low is smaller
					path = ['M', x - defaultWidth/2, open,
							'L', x + defaultWidth/2, open,
							'M', x, open,
							'L', x, low,
							'Z'
							];
				}else if( open == close ){ //high is bigger than low
					path = ['M', x - defaultWidth/2, open,
							'L', x + defaultWidth/2, open,
							'M', x, open,
							'L', x, high,
							'M', x, open,
							'L', x, low,
							'Z'
							];
				}else if( (open == high && low==close) ||( open ==low && high==close)){
					// draw a rect
					var rectx = x - defaultWidth/2;
					var recty = getMin(open,high,low,close);
					var rectWidth = defaultWidth;
					var rectHeight = high - low;
					path = renderer.symbols['square'](rectx, recty,rectWidth,rectHeight);
				}else{ //rect withone tailor or two tailors
					var min = mathMin(open,high,low,close);
					var rectMin = mathMin(open,close);
					var rectMax = mathMax(open,close);
					var max = mathMax(open,high,low,close);
					path = ['M',x,min,
							'L',x, rectMin,
							x + defaultWidth/2, rectMin,
							x + defaultWidth/2, rectMax,
							x, rectMax,
							x, max,
							'M', x, rectMax,
							'L',x - defaultWidth/2, rectMax,
							x - defaultWidth/2, rectMin,
							x, rectMin,
							'Z'
							];
				}
				//path is ready, learn highcharts to draw it
				var obj = renderer.path(path);
				var tempHeight = mathMax(open,high,low,close) - mathMin(open,high,low,close);
				extend(obj, {
					//symbolName: series.symbolCanName,  //skip symbolName, otherwise it will invoke symbolAttr with render.symbols[...]. error
					x: x,
					y: (open+high+low+close)/4,
					width: defaultWidth,
					height: tempHeight == 0? 1:tempHeight
				});
				return obj;
			}
			});
		}
	},
	translate: function(){
		var series = this;
		
		Series.prototype.translate.call(series);
		//generate open/high/low/close   translation
		
		var yAxis = series.yAxis;
		var i = series.points.length;
		var points = series.points;
		while(i--){
			points[i].plotOpen = !points[i].open ? null : yAxis.translate( points[i].open, 0,1,0,1);
			points[i].plotHigh = !points[i].high ? null : yAxis.translate( points[i].high, 0,1,0,1);
			points[i].plotLow = !points[i].low ? null : yAxis.translate( points[i].low, 0,1,0,1);
			points[i].plotClose = !points[i].close ? null : yAxis.translate( points[i].close, 0,1,0,1);
		}
	},
	/**
	 * Draw the markers
	 */
	drawPoints: function () {
		var series = this,
			pointAttr,
			points = series.points,
			chart = series.chart,
			plotX,
			plotY,
			i,
			point,
			radius,
			symbol,
			isImage,
			graphic;

		if (series.options.marker.enabled) {
			i = points.length;
			while (i--) {
				point = points[i];
				plotX = point.plotX;
				plotY = point.plotY;
				graphic = point.graphic;

				// only draw the point if y is defined
				if ((plotY !== UNDEFINED && !isNaN(plotY)) && point.validate()) {
					//before draw the points, let's reset pointAttr of states
					/*var tempColor = (point.open >=point.close) ? series.downColor : series.growColor;
					each([SELECT_STATE,NORMAL_STATE,HOVER_STATE], function (state){
						var tempAttr = point.pointAttr[state];
						tempAttr.fill = tempColor;
						tempAttr.stroke = tempColor;
						tempAttr["stroke-width"] = 1;
					});*/
					// shortcuts
					pointAttr = point.pointAttr[point.selected ? SELECT_STATE : NORMAL_STATE];
					radius = pointAttr.r;
					//symbol = pick(point.marker && point.marker.symbol, series.symbol);
					symbol = series.symbolCanName;
					isImage = symbol.indexOf('url') === 0;

					if (graphic) { // update, such as add point, need to change the x, y
						//graphic.attr(pointAttr);
						graphic.destroy();
					}
					/*	point.graphic = chart.renderer[symbol](
							
							plotX ,
							point.plotOpen,
							point.plotHigh,
							point.plotLow,
							point.plotClose
						)
						.attr(pointAttr)
						.add(series.group);*/
					//} else  {
						point.graphic = chart.renderer[symbol](
							
							plotX ,
							point.plotOpen,
							point.plotHigh,
							point.plotLow,
							point.plotClose
						)
						.attr(pointAttr)
						.add(series.group);
					//}
				}
			}
		}

	},
	getAttribs: function () {
		var series = this;
		Series.prototype.getAttribs.call(series);
		
		var points = series.points || [];
		i = points.length;
		//force to give each point its own attr
		while( i-- ){
			point = points[i];
			pointAttr = [];
			var tempColor = (point.open >=point.close) ? series.downColor : series.growColor;
			each([NORMAL_STATE,SELECT_STATE,HOVER_STATE], function (state){
				//if( pointAttr[state] ){
					pointAttr[state] = merge({}, {
						fill: (point.open >=point.close) ? series.downColor : series.growColor,
						stroke: (point.open >=point.close) ? series.downColor : series.growColor,
						'stroke-width': 1,
						r : series.pointAttr[state].r
					});
				//}
			});
			point.pointAttr = pointAttr;
		}
	},
	drawGraph: function(){},
	/**
	 * Draw the tracker object that sits above all data labels and markers to
	 * track mouse events on the graph or points. For the line type charts
	 * the tracker uses the same graphPath, but with a greater stroke width
	 * for better control.
	 */
	drawTracker: function () {
		var series = this,
			chart = series.chart,
			renderer = chart.renderer,
			shapeArgs,
			tracker,
			//trackerLabel = +new Date(),
			options = series.options,
			cursor = options.cursor,
			css = cursor && { cursor: cursor },
			trackerGroup = series.drawTrackerGroup(),
			rel,
			plotY,
			validPlotY;
			
		each(series.points, function (point) {
			tracker = point.tracker;
			//shapeArgs = point.trackerArgs || point.shapeArgs;
			plotY = point.plotY;
			validPlotY = !series.isCartesian || (plotY !== UNDEFINED && !isNaN(plotY));
			//delete shapeArgs.strokeWidth;
			if (point.y !== null && validPlotY && point.validate()) {
				if (tracker) {// update
					//tracker.attr(point.pointAttr[HOVER_STATE]);
					tracker.destroy();
				} //else {
					point.tracker =
						renderer[series.symbolCanName](point.plotX,
							point.plotOpen, point.plotHigh, point.plotLow, point.plotClose)
						.attr({
							isTracker: true,
							fill: TRACKER_FILL,
							visibility: series.visible ? VISIBLE : HIDDEN
						})
						.on(hasTouch ? 'touchstart' : 'mouseover', function (event) {
							//rel = event.relatedTarget || event.fromElement;
							if (chart.hoverSeries !== series ) {
								series.onMouseOver();
							}
							point.onMouseOver();

						})
						.on('mouseout', function (event) {
							if (!options.stickyTracking) {
								//rel = event.relatedTarget || event.toElement;
								//if (attr(rel, 'isTracker') !== trackerLabel) {
									series.onMouseOut();
								//}
							}
						})
						.css(css)
						.add( trackerGroup); // pies have point group - see issue #118
				//}
			}
		});
	}
});
seriesTypes.candlestick = CandelStickSeries;
	
