package model{
    import com.adobe.cairngorm.model.ModelLocator;
    
    import pm.EmployeeSelectorPm;
    import pm.MainPanelPm;
    import pm.ProjectDetailPm;

    [Bindable]
    public class ExamModelLocator implements ModelLocator{
        
        public var mainPanelPm:MainPanelPm ;
        public var projectDetailPm:ProjectDetailPm;
        public var employeeSelectorPm:EmployeeSelectorPm;
        
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
            projectDetailPm = new ProjectDetailPm();
            employeeSelectorPm = new EmployeeSelectorPm();
        }

    }
}