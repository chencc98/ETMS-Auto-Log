package model{
    import com.adobe.cairngorm.model.ModelLocator;
    
    import pm.MainPanelPm;

    [Bindable]
    public class ExamModelLocator implements ModelLocator{
        
        public var mainPanelPm:MainPanelPm ;
        
        private static var modelLocator : ExamModelLocator;

        public function ExamModelLocator(){
            if ( modelLocator != null ){
                throw new Error( "Only one ExamModelLocator instance should be instantiated" );    
            }

            initModel();
        }
        public static function getInstance() : ExamModelLocator {
            if ( modelLocator == null ){
                modelLocator = new ExamModelLocator();
            }
              
          return modelLocator;
        }

        private function initModel():void{
            mainPanelPm = new MainPanelPm();
        }

    }
}