package entity {
    import entity.ProjectStatus;
    public class Project{
        public var projectId:String;
        public var projectName:String;
        public var clientId:String;
        public var progress:Number;
        public var projectStatus:String;

        public function Project(){
            projectId = "";
            projectName = "";
            clientId = "";
            progress = 0;
            projectStatus = ProjectStatus.DEFAULT;
        }

        public function Project(id:String, name:String, cid:String,
                prog:Number, status:String)
        {
            projectId = id;
            projectName = name;
            clientId = cid;
            progress = prog;
            projectStatus = status;
        }


    }
}