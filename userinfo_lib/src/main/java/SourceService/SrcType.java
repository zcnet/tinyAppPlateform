package SourceService;
public enum SrcType
{
	eSrcType_Unknown(0),
	eSrcType_Tuner(1),
	eSrcType_Other(2),
	eSrcType_AUX(3),
	eSrcType_UserDefined1(4),
	eSrcType_UserDefined2(5);
	private int value = 0;
	private SrcType(int value) {this.value = value;}
	public int value() {return this.value;}
	public static SrcType valueOf(int v) {
		switch(v){
			case 0:return eSrcType_Unknown;
			case 1:return eSrcType_Tuner;
			case 2:return eSrcType_Other;
			case 3:return eSrcType_AUX;
			case 4:return eSrcType_UserDefined1;
			case 5:return eSrcType_UserDefined2;
			default:return null;
		}
	}

	public static class Ref {
		private SrcType v;
		public Ref(SrcType v){ this.v = v;}
		public void setValue(SrcType v) {this.v = v;}
		public SrcType getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(SrcType v){return new Ref(v);}}

