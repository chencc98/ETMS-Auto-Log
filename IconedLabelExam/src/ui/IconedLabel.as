package ui {
    import flash.display.Loader;
    import flash.display.LoaderInfo;
    import flash.events.Event;
    import flash.net.URLRequest;
    
    import mx.controls.Image;
    import mx.controls.Label;
    import mx.core.BitmapAsset;
    import mx.core.UIComponent;


[Style(name="horizontalGap", type="Number", format="Length", inherit="no")]
[Style(name="paddingLeft", type="Number", format="Length", inherit="no")]
[Style(name="paddingRight", type="Number", format="Length", inherit="no")]
[Style(name="verticalAlign", type="String", enumeration="bottom,middle,top", inherit="no")]
    public class IconedLabel extends UIComponent {

        private var _image:Image;
        private var _image_source_bm:BitmapAsset;
        private var _image_source_obj:Object;
        private var _image_source_str:String;
        private var isImageSourceChanged:Boolean = false;
        private var isImageSourceLoading:Boolean = false;
        private var _label:Label;
        private var _label_text:String;
        private var isLabelTextChanged:Boolean = false;
        
        public static const VALIGN_TOP:String = "top";
        public static const VALIGN_MIDDLE:String = "middle";
        public static const VALIGN_BOTTOM:String = "bottom";
        
        private static const TOP_BOTTOM_PADDING:Number = 5;
        
        
        [Embed(source="assets/COMPLETE.jpg")]
        private static var imgObj:Class;
        
        public function IconedLabel() {
            super();
            _label_text = "Default";
            _image_source_bm = new imgObj() as BitmapAsset;
            _image_source_str = "assets/COMPLETE.jpg";
        }
        
        private function setLocalStyleNumber(type:String, obj:Number):void{
            if (isNaN(this.getStyle(type)) ){
                this.setStyle(type, obj);
            }
        }
        
        private function setLocalStyleString(type:String, value:String):void{
            if (this.getStyle(type) == null || this.getStyle(type) == ""){
                this.setStyle(type,value);
            }
        }
        
        override protected function createChildren():void {
            super.createChildren();
            if (!_image) {
                _image = new Image();
            }
            _image.source = _image_source_bm;
            this.addChild(_image);
//            
//            
            if (!_label) {
                _label = new Label();
            }
            _label.text = _label_text;
            this.addChild(_label);
            
            setLocalStyleNumber("horizontalGap", 0);
            setLocalStyleNumber("paddingLeft", 0);
            setLocalStyleNumber("paddingRight", 0);
            setLocalStyleString("verticalAlign", IconedLabel.VALIGN_MIDDLE);
//            
        }
        
        override protected function commitProperties():void{
            super.commitProperties();

            if( isLabelTextChanged ){
                _label.text = _label_text;
                isLabelTextChanged = false;
            };
            if( isImageSourceChanged ){
                var load:Loader = new Loader();
                load.contentLoaderInfo.addEventListener(Event.COMPLETE, imageLoadComplete);
                var request:URLRequest = new URLRequest(_image_source_str);
                load.load(request);
                isImageSourceChanged = false;
                isImageSourceLoading = true;
            }
        }
        
        private function imageLoadComplete(event:Event):void{
            var loadinfo:LoaderInfo = event.target as LoaderInfo;
            _image.source = loadinfo.content;

            isImageSourceLoading = false;
            
            loadinfo.loader.removeEventListener(Event.COMPLETE, imageLoadComplete);
            invalidateSize();
            invalidateDisplayList();
        }
        
        
        [Bindable]
        public function set imageSource(s:String):void {
            if(_image_source_str == s || isImageSourceLoading == true){
                return;
            }
            _image_source_str = s;
            isImageSourceChanged = true;
            
            
            this.invalidateProperties();
            this.invalidateSize();
            this.invalidateDisplayList();
        }
        public function get imageSource():String{
            return _image_source_str;
        }
        
        [Bindable]
        public function get labelText():String{
            return _label_text;
        }
        public function set labelText(text:String):void{
            if(_label_text == text){
                return;
            }
            _label_text = text;
            isLabelTextChanged = true;
            this.invalidateProperties();
            this.invalidateSize();
            this.invalidateDisplayList();
        }
        

        
        override protected function measure():void {
            super.measure();
            var padding:Number = TOP_BOTTOM_PADDING;
            var _paddingLeft:Number = this.getStyle("paddingLeft");
            
            var _gap:Number = this.getStyle("horizontalGap");
            var _paddingRight:Number = this.getStyle("paddingRight");
            this.measuredWidth = this.measuredMinWidth = _paddingLeft 
                    + _image.getExplicitOrMeasuredWidth()
                    + _gap + _label.getExplicitOrMeasuredWidth() + _paddingRight;
            var tmph:Number = _image.getExplicitOrMeasuredHeight() > _label.getExplicitOrMeasuredHeight() 
                     ? _image.getExplicitOrMeasuredHeight()
                     : _label.getExplicitOrMeasuredHeight();
            this.measuredHeight = this.measuredMinHeight = tmph + padding * 2;
        }
        
        override protected function updateDisplayList(unscaledWidth:Number,
                unscaledHeight:Number):void 
        {
            super.updateDisplayList(unscaledWidth, unscaledHeight);
            var _paddingLeft:Number = this.getStyle("paddingLeft");
            var _gap:Number = this.getStyle("horizontalGap");
            var _paddingRight:Number = this.getStyle("paddingRight");

            var percentWidth:Number = 1;
            if( unscaledWidth >= this.measuredWidth ){
                percentWidth = 1;
            }else{
                percentWidth = unscaledWidth / this.measuredWidth;
            }
            var imageWidth:Number = _image.getExplicitOrMeasuredWidth() * percentWidth;
            var labelWidth:Number = _label.getExplicitOrMeasuredWidth() * percentWidth;
            var xLeft:Number = _paddingLeft * percentWidth;
            var gapWeight:Number = _gap * percentWidth;
            var xRight:Number = _paddingRight * percentWidth;
            
            var percentHeight:Number = 1;
            if (unscaledHeight >= this.measuredHeight){
                percentHeight = 1;
            }else{
                percentHeight = unscaledHeight / this.measuredHeight;
            }
            var imageHeight:Number = _image.getExplicitOrMeasuredHeight() * percentHeight;
            var labelHeight:Number = _label.getExplicitOrMeasuredHeight() * percentHeight;
            var yPadding:Number = TOP_BOTTOM_PADDING * percentHeight;
            
            if (_image.width != imageWidth || _image.height != imageHeight){
                _image.setActualSize(imageWidth, imageHeight);
//                _image.scaleX = imageWidth / _image.contentWidth;
//                _image.scaleY = imageHeight / _image.contentHeight;
            }
            if (_label.width != labelWidth || _label.height != labelHeight){
                _label.setActualSize(labelWidth, labelHeight);
            }
            

            var _valign:String = this.getStyle("verticalAlign");
            _image.move(xLeft, 
                    calculateYPoint(_valign, _image.height, unscaledHeight,yPadding));
            _label.move(xLeft + _image.width + gapWeight, 
                    calculateYPoint(_valign, _label.height,unscaledHeight, yPadding));
        }
        
        private function calculateYPoint(valign:String, actualHeight:Number, 
                totalHeight:Number, padding:Number):Number
        {
            var ypoint:Number = 0;
            if( valign == IconedLabel.VALIGN_TOP){
                ypoint = padding;
            }else if (valign == IconedLabel.VALIGN_BOTTOM){
                if( padding == TOP_BOTTOM_PADDING){
                    ypoint = this.measuredHeight - padding - actualHeight;
                }else{
                    ypoint = (totalHeight - padding - actualHeight);
                }
            }else{
                if (padding == TOP_BOTTOM_PADDING) {
                    ypoint = (this.measuredHeight - actualHeight)/2;
                }else{
                    ypoint = (totalHeight - actualHeight)/2;
                }
            }
            return ypoint;
        }
        
        override public function styleChanged(styleProp:String):void {
            super.styleChanged(styleProp);
//            invalidateProperties();
            invalidateSize();
            invalidateDisplayList();
        }
        
    }
}