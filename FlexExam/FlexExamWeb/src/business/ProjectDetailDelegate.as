package business{
    import com.adobe.cairngorm.business.ServiceLocator;
    import mx.rpc.IResponder;
    
    public class ProjectDetailDelegate{
        private var service:Object;
        private var responder:IResponder;
        //private var projectid:String;
        
        
        public function ProjectDetailDelegate(responder : IResponder){
            service = ServiceLocator.getInstance().getRemoteObject(Services.FLEX_EXAM_SERVICE);
            this.responder = responder;
            //this.projectid = pid;
        }
        public function loadEmployee(projectid:String):void{
            var call:Object = service.loadEmployeeInProjectDetail(projectid);
            call.addResponder(this.responder);
            
        }

    }
}