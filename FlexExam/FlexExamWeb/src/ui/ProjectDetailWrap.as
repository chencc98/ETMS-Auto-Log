package ui {
    import ui.ProjectDetail;
    public class ProjectDetailWrap {
        private static var projectDetail:ProjectDetail = null;
        public function ProjectDetailWrap()
        {
            //TODO: implement function
        }
        
        public static function getProjectDetailUI():ProjectDetail {
            if (projectDetail == null){
                projectDetail = new ProjectDetail();
            }
            return projectDetail;
        }

    }
}