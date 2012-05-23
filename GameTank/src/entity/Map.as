package entity
{
    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    [RemoteClass(alias="entity.Map")]
    public class Map implements IExternalizable
    {
        public var description:String;
        public var mapId:String;
        
        private var blockType:Array;            //21 x 20
        public function Map()
        {
            this.description = "";
            this.mapId = "";
            this.blockType = new Array(21 * 20);
            this.blockType.forEach(initialBlock , this);
        }
        
        
        //only for array to assign the initial data
        private function initialBlock(element:*, index:Number, arr:Array):void{
            arr[index] = 0;
        }
        
        
        public function readExternal(input:IDataInput):void{
            this.description = input.readUTF();
            this.mapId = input.readUTF();
            this.blockType = input.readObject() as Array;
        }
        public function writeExternal(output:IDataOutput):void{
            output.writeUTF(this.description);
            output.writeUTF(this.mapId);
            output.writeObject(this.blockType);
        }

    }
}