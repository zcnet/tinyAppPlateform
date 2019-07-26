package MTunerService;
public enum EStationState
{
	TUNER_UNKNOW(0),
	TUNER_BROADCASTING(1),
	TUNER_TUNING(2),
	TUNER_SEARCHING(3),
	TUNER_LEARNING(4),
	TUNER_SWITCHING_BAND(5);
	private int value = 0;
	private EStationState(int value) {this.value = value;}
	public int value() {return this.value;}
	public static EStationState valueOf(int v) {
		switch(v){
			case 0:return TUNER_UNKNOW;
			case 1:return TUNER_BROADCASTING;
			case 2:return TUNER_TUNING;
			case 3:return TUNER_SEARCHING;
			case 4:return TUNER_LEARNING;
			case 5:return TUNER_SWITCHING_BAND;
			default:return null;
		}
	}

	public static class Ref {
		private EStationState v;
		public Ref(EStationState v){ this.v = v;}
		public void setValue(EStationState v) {this.v = v;}
		public EStationState getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(EStationState v){return new Ref(v);}}

