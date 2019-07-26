package MTunerService;
public enum EStationType
{
	STATIONTYPE_UNKNOWN(0),
	STATIONTYPE_AM(1),
	STATIONTYPE_FM(2);
	private int value = 0;
	private EStationType(int value) {this.value = value;}
	public int value() {return this.value;}
	public static EStationType valueOf(int v) {
		switch(v){
			case 0:return STATIONTYPE_UNKNOWN;
			case 1:return STATIONTYPE_AM;
			case 2:return STATIONTYPE_FM;
			default:return null;
		}
	}

	public static class Ref {
		private EStationType v;
		public Ref(EStationType v){ this.v = v;}
		public void setValue(EStationType v) {this.v = v;}
		public EStationType getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(EStationType v){return new Ref(v);}}

