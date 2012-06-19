package pm{
    import entity.MainPanelProject;
    
    import mx.collections.ArrayCollection;
    
    
    
    [Bindable]
    public class ProjectDetailPm{
        public var projectDetail:MainPanelProject;
        public var isEnableProjectProgress:Boolean;
        public var employeeList:ArrayCollection;
        public var isEnableAddEmployeeButton:Boolean = false;
        public var isEnableDelEmployeeButton:Boolean = false;
        public var isEnableSubmitButton:Boolean;
        public var isEnableEditButton:Boolean;
        public var isVisibleEditButton:Boolean;
        public function ProjectDetailPm(){
            this.projectDetail = new MainPanelProject();
            this.isEnableProjectProgress = false;
            this.employeeList = new ArrayCollection();
            
            this.isEnableSubmitButton = false;
            this.isEnableEditButton = true;
            this.isVisibleEditButton = true;
        }

    }
}