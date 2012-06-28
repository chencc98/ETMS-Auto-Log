package pm{
    import cairngormevents.ProjectDetailPopUpEvent;
    
    import com.adobe.cairngorm.control.CairngormEventDispatcher;
    
    import entity.Employee;
    import entity.MainPanelProject;
    import entity.ProjectStatus;
    
    import mx.collections.ArrayCollection;
    
    
    [Bindable]
    public class ProjectDetailPm{
        public var projectDetail:MainPanelProject;
        
        public var employeeList:ArrayCollection;
        public var isEditable:Boolean;
        public var isReadOnlyStatus:Boolean;
        
        public function ProjectDetailPm(){
            this.projectDetail = new MainPanelProject();
            
            this.employeeList = new ArrayCollection();
            
            this.isEditable = true;
            this.isReadOnlyStatus = true;
        }
        
        public function setData(pd:MainPanelProject, list:ArrayCollection=null, fetch:Boolean=true):void{
            this.projectDetail = pd;
            if( pd.status == ProjectStatus.COMPLETE ){
                this.isEditable = false;
            }else{
                this.isEditable = true;
            }
            this.isReadOnlyStatus = true;
            
            if( fetch ){
                var popEvent:ProjectDetailPopUpEvent = new ProjectDetailPopUpEvent(ProjectDetailPopUpEvent.PROJECT_DETAIL,
                        this.projectDetail,this, "employeeList");
                CairngormEventDispatcher.getInstance().dispatchEvent(popEvent);
            }else if( list == null ){
                this.employeeList = new ArrayCollection();
            }else{
                this.employeeList = list;
            }
        }
        
        public function startEdit():void{
            if( this.isEditable ){
                this.isReadOnlyStatus = false;
            }
        }
        
        public function addEmployee(emp:Employee):String{
            var employeelist:ArrayCollection = this.employeeList;
            //search the dataprovider, if the employee exist, ignore
            var len:Number = employeelist.length;
            for( var i:Number=0; i< len; i++){
                if( employeelist[i].id == emp.id ){
                    return "Existing Employee [" + emp.name 
                            + "] detected, ignore!";
                    
                }
            }

            //add this employee data into data provider
            
            employeelist.addItem(emp);
            return "";
        }

    }
}