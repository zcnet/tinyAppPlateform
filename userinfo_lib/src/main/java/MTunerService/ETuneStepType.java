package MTunerService;
public enum ETuneStepType
{
	TUNESTEPTYPE_UNKNOWN(0),
	TUNESTEPTYPE_FORWARD(1),
	TUNESTEPTYPE_BACKWARD(2);
	private int value = 0;
	private ETuneStepType(int value) {this.value = value;}
	public int value() {return this.value;}
	public static ETuneStepType valueOf(int v) {
		switch(v){
			case 0:return TUNESTEPTYPE_UNKNOWN;
			case 1:return TUNESTEPTYPE_FORWARD;
			case 2:return TUNESTEPTYPE_BACKWARD;
			default:return null;
		}
	}

	public static class Ref {
		private ETuneStepType v;
		public Ref(ETuneStepType v){ this.v = v;}
		public void setValue(ETuneStepType v) {this.v = v;}
		public ETuneStepType getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(ETuneStepType v){return new Ref(v);}}

