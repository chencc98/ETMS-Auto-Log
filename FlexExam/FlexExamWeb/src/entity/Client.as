package entity {
    public class Client {
        public var clientId:String;
        public var clientName:String;

        public function Client(){
            clientId = "";
            clientName = "";
        }
        public function Client(id:String, name:String){
            clientId = id;
            clientName = name;
        }



    }
}