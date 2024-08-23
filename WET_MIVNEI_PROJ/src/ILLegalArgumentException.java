public class ILLegalArgumentException extends RuntimeException  {
        public ILLegalArgumentException(){}
        public ILLegalArgumentException(String message){
            super(message);
        }
        public ILLegalArgumentException(String message,Throwable cause){
            super(message,cause);
        }
}
