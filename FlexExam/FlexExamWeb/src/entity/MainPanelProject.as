package entity{
    import com.adobe.cairngorm.vo.ValueObject;
    
    [RemoteClass(alias="com.ssga.flexexam.service.entity.MainPanelProject")]
    public class MainPanelProject implements ValueObject{
        public var id:String;
        public var name:String;
        public var progress:int;
        public var status:String;
        public var client:String;
        public var employee:String;
        public function MainPanelProject(){
        }

    }
}