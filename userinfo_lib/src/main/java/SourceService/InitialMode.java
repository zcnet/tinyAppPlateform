package SourceService;
public enum InitialMode
{
	eInitialMode_Unknown(0),
	eInitialMode_TODON(1),
	eInitialMode_TODOFF(2);
	private int value = 0;
	private InitialMode(int value) {this.value = value;}
	public int value() {return this.value;}
	public static InitialMode valueOf(int v) {
		switch(v){
			case 0:return eInitialMode_Unknown;
			case 1:return eInitialMode_TODON;
			case 2:return eInitialMode_TODOFF;
			default:return null;
		}
	}

	public static class Ref {
		private InitialMode v;
		public Ref(InitialMode v){ this.v = v;}
		public void setValue(InitialMode v) {this.v = v;}
		public InitialMode getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(InitialMode v){return new Ref(v);}}

