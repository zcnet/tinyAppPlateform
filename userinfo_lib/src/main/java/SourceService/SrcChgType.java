package SourceService;
public enum SrcChgType
{
	eSrcChgType_Unknown(0),
	eSrcChgType_SrcChange(1),
	eSrcChgType_Normal(2),
	eSrcChgType_Error(3);
	private int value = 0;
	private SrcChgType(int value) {this.value = value;}
	public int value() {return this.value;}
	public static SrcChgType valueOf(int v) {
		switch(v){
			case 0:return eSrcChgType_Unknown;
			case 1:return eSrcChgType_SrcChange;
			case 2:return eSrcChgType_Normal;
			case 3:return eSrcChgType_Error;
			default:return null;
		}
	}

	public static class Ref {
		private SrcChgType v;
		public Ref(SrcChgType v){ this.v = v;}
		public void setValue(SrcChgType v) {this.v = v;}
		public SrcChgType getValue() {return v;}		public int value() {return v.value;}	}
	public static Ref valueOf(SrcChgType v){return new Ref(v);}}

