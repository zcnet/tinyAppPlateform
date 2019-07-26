package MTunerService;
public enum EScanType
{
	SCANTYPE_UNKNOWN(0),
	SCANTYPE_AM(1),
	SCANTYPE_FM(2),
	SCANTYPE_STOP(3);
	private int value = 0;
	private EScanType(int value) {this.value = value;}
	public int value() {return this.value;}
	public static EScanType valueOf(int v) {
		switch(v){
			case 0:return SCANTYPE_UNKNOWN;
			case 1:return SCANTYPE_AM;
			case 2:return SCANTYPE_FM;
			case 3:return SCANTYPE_STOP;
			default:return null;
		}
	}

	public static class Ref {
		private EScanType v;
		public Ref(EScanType v){ this.v = v;}
		public void setValue(EScanType v) {this.v = v;}
		public EScanType getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(EScanType v){return new Ref(v);}}

