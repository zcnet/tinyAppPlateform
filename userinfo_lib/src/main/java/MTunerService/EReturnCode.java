package MTunerService;
public enum EReturnCode
{
	RETURN_ERR_NONE(0),
	RETURN_ERR_GENERAL(1),
	RETURN_ERR_PARAMETER_UNKNOWN(2),
	RETURN_ERR_NOTIN_TUNER_SOURCE(3),
	RETURN_ERR_UNKNOWN(4);
	private int value = 0;
	private EReturnCode(int value) {this.value = value;}
	public int value() {return this.value;}
	public static EReturnCode valueOf(int v) {
		switch(v){
			case 0:return RETURN_ERR_NONE;
			case 1:return RETURN_ERR_GENERAL;
			case 2:return RETURN_ERR_PARAMETER_UNKNOWN;
			case 3:return RETURN_ERR_NOTIN_TUNER_SOURCE;
			case 4:return RETURN_ERR_UNKNOWN;
			default:return null;
		}
	}

	public static class Ref {
		private EReturnCode v;
		public Ref(EReturnCode v){ this.v = v;}
		public void setValue(EReturnCode v) {this.v = v;}
		public EReturnCode getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(EReturnCode v){return new Ref(v);}}

