package pm{
    import mx.collections.ArrayCollection;
    import mx.collections.XMLListCollection;
    
    public class MainPanelPm{
        [Bindable]
        public var employeeList:ArrayCollection;
        public function MainPanelPm() {
            this.employeeList = new ArrayCollection();
        }

    }
}