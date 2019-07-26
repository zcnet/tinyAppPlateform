package MTunerService;
public enum ESeekType
{
	SEEKTYPE_UNKNOWN(0),
	SEEKTYPE_SEEKUP(1),
	SEEKTYPE_SEEKDOWN(2),
	SEEKTYPE_STOP(3);
	private int value = 0;
	private ESeekType(int value) {this.value = value;}
	public int value() {return this.value;}
	public static ESeekType valueOf(int v) {
		switch(v){
			case 0:return SEEKTYPE_UNKNOWN;
			case 1:return SEEKTYPE_SEEKUP;
			case 2:return SEEKTYPE_SEEKDOWN;
			case 3:return SEEKTYPE_STOP;
			default:return null;
		}
	}

	public static class Ref {
		private ESeekType v;
		public Ref(ESeekType v){ this.v = v;}
		public void setValue(ESeekType v) {this.v = v;}
		public ESeekType getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(ESeekType v){return new Ref(v);}}

