package MTunerService;
public enum EAudioType
{
	TUNER_UNKNOWN(0),
	TUNER_MONO(1),
	TUNER_STEREO(2),
	TUNER_NOAUDIO(3);
	private int value = 0;
	private EAudioType(int value) {this.value = value;}
	public int value() {return this.value;}
	public static EAudioType valueOf(int v) {
		switch(v){
			case 0:return TUNER_UNKNOWN;
			case 1:return TUNER_MONO;
			case 2:return TUNER_STEREO;
			case 3:return TUNER_NOAUDIO;
			default:return null;
		}
	}

	public static class Ref {
		private EAudioType v;
		public Ref(EAudioType v){ this.v = v;}
		public void setValue(EAudioType v) {this.v = v;}
		public EAudioType getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(EAudioType v){return new Ref(v);}}

