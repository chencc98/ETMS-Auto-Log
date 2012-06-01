package business{
    import com.adobe.cairngorm.business.ServiceLocator;
    
    import mx.rpc.IResponder;
    
    public class MainPanelInitDelegate{
        private var service:Object;
        private var responder:IResponder;
        
        public function MainPanelInitDelegate(responder : IResponder ){
            service = ServiceLocator.getInstance().getRemoteObject(Services.FLEX_EXAM_SERVICE);
            this.responder = responder;
        }

        public function loadEmployee():void{
            var call:Object = service.loadMainEmployee();
            call.addResponder(this.responder);
            
        }
    }
}