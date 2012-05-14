package ui {
    import ui.EmployeeSelector;
    public class EmployeeSelectorWrap {
        private static var employeeSelector:EmployeeSelector = null;
        public function EmployeeSelectorWrap()
        {
        }

        public static function getEmployeeSelector():EmployeeSelector{
            if (employeeSelector == null){
                employeeSelector = new EmployeeSelector();
            }
            return employeeSelector;
        }

    }
}