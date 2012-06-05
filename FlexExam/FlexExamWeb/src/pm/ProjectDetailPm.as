package pm{
    import mx.collections.ArrayCollection;
    
    import entity.MainPanelProject;
    
    
    
    [Bindable]
    public class ProjectDetailPm{
        public var projectDetail:MainPanelProject;
        public var employeeList:ArrayCollection;
        public function ProjectDetailPm(){
            this.projectDetail = new MainPanelProject();
            this.employeeList = new ArrayCollection();
        }

    }
}