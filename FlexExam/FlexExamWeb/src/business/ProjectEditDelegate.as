package business{
    import com.adobe.cairngorm.business.ServiceLocator;
    import mx.rpc.IResponder;
    
    public class ProjectEditDelegate {
        private var service:Object;
        private var responder:IResponder;
        
        public function ProjectEditDelegate(responder : IResponder){
            service = ServiceLocator.getInstance().getRemoteObject(Services.FLEX_EXAM_SERVICE);
            this.responder = responder;
        }
        
        public function editProject(pid:String, progress:String, list:String):void{
            var call:Object = service.editProject(pid, progress, list);
            call.addResponder(this.responder);
        }
        

    }
}