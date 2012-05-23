package entity
{
    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    
    [RemoteClass(alias="entity.Config")]
    public class Config implements IExternalizable
    {
        public var startmap:String;
        public function Config()
        {
            this.startmap = "1";
        }


        public function readExternal(input:IDataInput):void{
            this.startmap = input.readUTF();
        }
        public function writeExternal(output:IDataOutput):void{
            output.writeUTF(this.startmap);
        }
    }
}