package pm{
    import mx.collections.ArrayCollection;
    
    public class EmployeeSelectorPm{
        [Bindable]
        public var employeeList:ArrayCollection;
        public function EmployeeSelectorPm(){
            employeeList = new ArrayCollection();
        }

    }
}