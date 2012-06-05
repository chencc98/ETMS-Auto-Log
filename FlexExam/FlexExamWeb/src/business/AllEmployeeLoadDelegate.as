package business{
    import com.adobe.cairngorm.business.ServiceLocator;
    import mx.rpc.IResponder;
    
    public class AllEmployeeLoadDelegate {
        private var service:Object;
        private var responder:IResponder;
        
        public function AllEmployeeLoadDelegate(responder : IResponder) {
            service = ServiceLocator.getInstance().getRemoteObject(Services.FLEX_EXAM_SERVICE);
            this.responder = responder;
        }
        
        public function loadEmployee():void{
            var call:Object = service.loadAllEmployeeAsSelector();
            call.addResponder(this.responder);
            
        }

    }
}