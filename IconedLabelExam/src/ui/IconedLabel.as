package ui {
    import mx.controls.Image;
    import mx.controls.Label;
    import mx.core.BitmapAsset;
    import mx.core.UIComponent;

[Style(name="verticalAlign", type="String", enumeration="bottom,middle,top", inherit="no")]
[Style(name="horizontalGap", type="Number", format="Length", inherit="no")]
[Style(name="paddingLeft", type="Number", format="Length", inherit="no")]
[Style(name="paddingRight", type="Number", format="Length", inherit="no")]
    public class IconedLabel extends UIComponent {
//        private var _gap:Number;
//        private var _width:Number;
//        private var _valign:String;   
//        private var _paddingLeft:Number;    
//        private var _paddingRight:Number;    
        
        private var _image:Image;
        private var _image_source_bm:BitmapAsset;
        private var _image_source_obj:Object;
        private var isImageSourceChanged:Boolean = false;
        private var _label:Label;
        private var _label_text:String;
        
        public static const VALIGN_TOP:String = "top";
        public static const VALIGN_MIDDLE:String = "middle";
        public static const VALIGN_BOTTOM:String = "bottom";
        
        private static const TOP_BOTTOM_PADDING:Number = 5;
        
        
        [Embed(source="assets/COMPLETE.jpg")]
        [Bindable]
        private var imgObj:Class;
        
        public function IconedLabel() {
            super();
//            _gap = super.getStyle("horizontalGap");
//            _width = super.width;
//            _valign = super.getStyle("verticalAlign");
//            _paddingLeft = super.getStyle("paddingLeft");
//            _paddingRight = super.getStyle("paddingRight");
            _label_text = "Default";
            _image_source_bm = new imgObj() as BitmapAsset;
        }
        
        override protected function createChildren():void {
            super.createChildren();
            if (!_image) {
                _image = new Image();
            }
//                _image_source = new imgObj() as BitmapAsset;
//                _image.source = _image_source;
            if (!_image_source_obj){
                _image.source = _image_source_bm;
            }else{
                _image.load(_image_source_obj);
                isImageSourceChanged = false;
            }
            this.addChild(_image);
            
            
            if (!_label) {
                _label = new Label();
            }
            _label.text = _label_text;
            this.addChild(_label);
            
        }
        
        override protected function commitProperties():void{
            super.commitProperties();
//            _gap = super.getStyle("horizontalGap");

//            _valign = super.getStyle("verticalAlign");
//            _paddingLeft = super.getStyle("paddingLeft");
//            _paddingRight = super.getStyle("paddingRight");
            if( _label.text != _label_text ){
                _label.text = _label_text;
            };
            if( isImageSourceChanged ){
                _image.load(_image_source_obj);
                isImageSourceChanged = false;
            }
        }
        
        
        
        public function set imageSource(s:Object):void {
            _image_source_obj = s;
            isImageSourceChanged = true;
            
            this.invalidateProperties();
            this.invalidateSize();
            this.invalidateDisplayList();
        }
        
        public function get labelText():String{
            return _label_text;
        }
        public function set labelText(text:String):void{
            _label_text = text;
            this.invalidateProperties();
            this.invalidateSize();
            this.invalidateDisplayList();
        }
        
        private function getImageMeasureWidth():Number {
            return _image.getExplicitOrMeasuredWidth() == 0 || isNaN(_image.getExplicitOrMeasuredWidth()) ? 50 
                    : _image.getExplicitOrMeasuredWidth();
        }
        private function getImageMeasureHeight():Number {
            return _image.getExplicitOrMeasuredHeight() == 0 || isNaN(_image.getExplicitOrMeasuredHeight())? 50
                    : _image.getExplicitOrMeasuredHeight();
        }
        
        override protected function measure():void {
            super.measure();
            var padding:Number = TOP_BOTTOM_PADDING;
            var _paddingLeft:Number = super.getStyle("paddingLeft");
            var _gap:Number = super.getStyle("horizontalGap");
            var _paddingRight:Number = super.getStyle("paddingRight");
            this.measuredWidth = this.measuredMinWidth = _paddingLeft 
                    + getImageMeasureWidth()
                    + _gap + _label.getExplicitOrMeasuredWidth() + _paddingRight;
            var tmph:Number = getImageMeasureHeight() > _label.getExplicitOrMeasuredHeight() 
                     ? getImageMeasureHeight()
                     : _label.getExplicitOrMeasuredHeight();
            this.measuredHeight = this.measuredMinHeight = tmph + padding * 2;
        }
        
        override protected function updateDisplayList(unscaledWidth:Number,
                unscaledHeight:Number):void 
        {
            super.updateDisplayList(unscaledWidth, unscaledHeight);
            var _paddingLeft:Number = super.getStyle("paddingLeft");
            var _gap:Number = super.getStyle("horizontalGap");
            var _paddingRight:Number = super.getStyle("paddingRight");
            var xLeft:Number;
            var gapWeight:Number;
            var xRight:Number;
            var yPadding:Number;
            var imageWidth:Number;
            var labelWidth:Number;
            var imageHeight:Number;
            var labelHeight:Number;
            var percent:Number;
            if( unscaledWidth >= this.measuredWidth ){
                percent = 1;
            }else{
                percent = unscaledWidth / this.measuredWidth;
            }
            imageWidth = getImageMeasureWidth() * percent;
            labelWidth = _label.getExplicitOrMeasuredWidth() * percent;
            xLeft = _paddingLeft * percent;
            gapWeight = _gap * percent;
            xRight = _paddingRight * percent;
            if (unscaledHeight >= this.measuredHeight){
                percent = 1;
            }else{
                percent = unscaledHeight / this.measuredHeight;
            }
            imageHeight = getImageMeasureHeight() * percent;
            labelHeight = _label.getExplicitOrMeasuredHeight() * percent;
            yPadding = TOP_BOTTOM_PADDING * percent;
            
            if (_image.width != imageWidth || _image.height != imageHeight){
                _image.setActualSize(imageWidth, imageHeight);
                if (Math.abs(_image.scaleX * _image.contentWidth - imageWidth) >=3 ){
                    if(_image.contentWidth == 0){
                        _image.scaleX = 1;
                    }else{
                        _image.scaleX = imageWidth / _image.contentWidth;
                    }
                }
                if (Math.abs(_image.scaleY * _image.contentHeight - imageHeight) >=3 ){
                    if (_image.contentHeight == 0){
                        _image.scaleY = 1;
                    }else{
                        _image.scaleY = imageHeight / _image.contentHeight;
                    }
                }
//                _image.scaleX = imageWidth / _image.content.width;
//                _image.scaleY = imageHeight / _image.content.height;
            }
            if (_label.width != labelWidth || _label.height != labelHeight){
                _label.setActualSize(labelWidth, labelHeight);
            }
            

            var _valign:String = super.getStyle("verticalAlign");
            if (_valign == null || _valign == ""){
                _valign = IconedLabel.VALIGN_MIDDLE;
                super.setStyle("verticalAlign", _valign);
            }
            _image.move(xLeft, calculateYPoint(_valign, _image.height, unscaledHeight,
                    yPadding));
            _label.move(xLeft + _image.width + gapWeight, calculateYPoint(_valign, _label.height,
                    unscaledHeight, yPadding) );
        }
        
        private function calculateYPoint(valign:String, actualHeight:Number, 
                totalHeight:Number, padding:Number):Number
        {
            if( valign == IconedLabel.VALIGN_TOP){
                return padding;
            }else if (valign == IconedLabel.VALIGN_BOTTOM){
                if( padding == TOP_BOTTOM_PADDING){
                    return this.measuredHeight - padding - actualHeight;
                }else{
                    return (totalHeight - padding - actualHeight);
                }
            }else{
                if (padding == TOP_BOTTOM_PADDING) {
                    return (this.measuredHeight - actualHeight)/2;
                }else{
                    return (totalHeight - actualHeight)/2;
                }
            }
        }
        
        override public function styleChanged(styleProp:String):void {
            super.styleChanged(styleProp);
//            invalidateProperties();
            invalidateSize();
            invalidateDisplayList();
        }
        
    }
}