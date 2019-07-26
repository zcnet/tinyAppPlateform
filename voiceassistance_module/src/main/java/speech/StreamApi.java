// Generated by the protocol buffer compiler.  DO NOT EDIT!

package speech;

@SuppressWarnings("hiding")
public interface StreamApi {
  
  // enum APIReqType
  public static final int API_REQ_TYPE_PARAM = 1;
  public static final int API_REQ_TYPE_DATA = 2;
  public static final int API_REQ_TYPE_LAST = 3;
  public static final int API_REQ_TYPE_CANCEL = 4;
  public static final int API_REQ_TYPE_THIRD_DATA = 5;
  
  // enum APIRespType
  public static final int API_RESP_TYPE_MIDDLE = 1;
  public static final int API_RESP_TYPE_RES = 2;
  public static final int API_RESP_TYPE_THIRD = 3;
  public static final int API_RESP_TYPE_HEART = 4;
  public static final int API_RESP_TYPE_LAST = 5;
  
  public static final class ApiParam extends
      com.google.protobuf.nano.MessageNano {
    
    private static volatile ApiParam[] _emptyArray;
    public static ApiParam[] emptyArray() {
      // Lazily initializes the empty array
      if (_emptyArray == null) {
        synchronized (
            com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
          if (_emptyArray == null) {
            _emptyArray = new ApiParam[0];
          }
        }
      }
      return _emptyArray;
    }
    
    // required string cuid = 1;
    public String cuid;
    
    // optional string appid = 2;
    public String appid;
    
    // optional string apikey = 3;
    public String apikey;
    
    // optional string chunk_key = 4;
    public String chunkKey;
    
    // required int32 sample_rate = 5;
    public Integer sampleRate;
    
    // required string format = 6;
    public String format;
    
    // required int64 task_id = 7;
    public Long taskId;
    
    // optional string pam = 8;
    public String pam;
    
    // optional bool early_return = 9;
    public Boolean earlyReturn;
    
    public ApiParam() {
      clear();
    }
    
    public ApiParam clear() {
      cuid = null;
      appid = null;
      apikey = null;
      chunkKey = null;
      sampleRate = null;
      format = null;
      taskId = null;
      pam = null;
      earlyReturn = null;
      cachedSize = -1;
      return this;
    }
    
    @Override
    public void writeTo(com.google.protobuf.nano.CodedOutputByteBufferNano output)
        throws java.io.IOException {
      output.writeString(1, this.cuid);
      if (this.appid != null) {
        output.writeString(2, this.appid);
      }
      if (this.apikey != null) {
        output.writeString(3, this.apikey);
      }
      if (this.chunkKey != null) {
        output.writeString(4, this.chunkKey);
      }
      output.writeInt32(5, this.sampleRate);
      output.writeString(6, this.format);
      output.writeInt64(7, this.taskId);
      if (this.pam != null) {
        output.writeString(8, this.pam);
      }
      if (this.earlyReturn != null) {
        output.writeBool(9, this.earlyReturn);
      }
      super.writeTo(output);
    }
    
    @Override
    protected int computeSerializedSize() {
      int size = super.computeSerializedSize();
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeStringSize(1, this.cuid);
      if (this.appid != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
            .computeStringSize(2, this.appid);
      }
      if (this.apikey != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
            .computeStringSize(3, this.apikey);
      }
      if (this.chunkKey != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
            .computeStringSize(4, this.chunkKey);
      }
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeInt32Size(5, this.sampleRate);
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeStringSize(6, this.format);
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeInt64Size(7, this.taskId);
      if (this.pam != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
            .computeStringSize(8, this.pam);
      }
      if (this.earlyReturn != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
            .computeBoolSize(9, this.earlyReturn);
      }
      return size;
    }
    
    @Override
    public ApiParam mergeFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            return this;
          default: {
            if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
              return this;
            }
            break;
          }
          case 10: {
            this.cuid = input.readString();
            break;
          }
          case 18: {
            this.appid = input.readString();
            break;
          }
          case 26: {
            this.apikey = input.readString();
            break;
          }
          case 34: {
            this.chunkKey = input.readString();
            break;
          }
          case 40: {
            this.sampleRate = input.readInt32();
            break;
          }
          case 50: {
            this.format = input.readString();
            break;
          }
          case 56: {
            this.taskId = input.readInt64();
            break;
          }
          case 66: {
            this.pam = input.readString();
            break;
          }
          case 72: {
            this.earlyReturn = input.readBool();
            break;
          }
        }
      }
    }
    
    public static ApiParam parseFrom(byte[] data)
        throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
      return com.google.protobuf.nano.MessageNano.mergeFrom(new ApiParam(), data);
    }
    
    public static ApiParam parseFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      return new ApiParam().mergeFrom(input);
    }
  }
  
  public static final class ApiData extends
      com.google.protobuf.nano.MessageNano {
    
    private static volatile ApiData[] _emptyArray;
    public static ApiData[] emptyArray() {
      // Lazily initializes the empty array
      if (_emptyArray == null) {
        synchronized (
            com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
          if (_emptyArray == null) {
            _emptyArray = new ApiData[0];
          }
        }
      }
      return _emptyArray;
    }
    
    // required uint32 len = 1;
    public Integer len;
    
    // required bytes post_data = 2;
    public byte[] postData;
    
    public ApiData() {
      clear();
    }
    
    public ApiData clear() {
      len = null;
      postData = null;
      cachedSize = -1;
      return this;
    }
    
    @Override
    public void writeTo(com.google.protobuf.nano.CodedOutputByteBufferNano output)
        throws java.io.IOException {
      output.writeUInt32(1, this.len);
      output.writeBytes(2, this.postData);
      super.writeTo(output);
    }
    
    @Override
    protected int computeSerializedSize() {
      int size = super.computeSerializedSize();
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeUInt32Size(1, this.len);
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeBytesSize(2, this.postData);
      return size;
    }
    
    @Override
    public ApiData mergeFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            return this;
          default: {
            if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
              return this;
            }
            break;
          }
          case 8: {
            this.len = input.readUInt32();
            break;
          }
          case 18: {
            this.postData = input.readBytes();
            break;
          }
        }
      }
    }
    
    public static ApiData parseFrom(byte[] data)
        throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
      return com.google.protobuf.nano.MessageNano.mergeFrom(new ApiData(), data);
    }
    
    public static ApiData parseFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      return new ApiData().mergeFrom(input);
    }
  }
  
  public static final class ApiLast extends
      com.google.protobuf.nano.MessageNano {
    
    private static volatile ApiLast[] _emptyArray;
    public static ApiLast[] emptyArray() {
      // Lazily initializes the empty array
      if (_emptyArray == null) {
        synchronized (
            com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
          if (_emptyArray == null) {
            _emptyArray = new ApiLast[0];
          }
        }
      }
      return _emptyArray;
    }
    
    public ApiLast() {
      clear();
    }
    
    public ApiLast clear() {
      cachedSize = -1;
      return this;
    }
    
    @Override
    public ApiLast mergeFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            return this;
          default: {
            if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
              return this;
            }
            break;
          }
        }
      }
    }
    
    public static ApiLast parseFrom(byte[] data)
        throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
      return com.google.protobuf.nano.MessageNano.mergeFrom(new ApiLast(), data);
    }
    
    public static ApiLast parseFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      return new ApiLast().mergeFrom(input);
    }
  }
  
  public static final class ApiHeart extends
      com.google.protobuf.nano.MessageNano {
    
    private static volatile ApiHeart[] _emptyArray;
    public static ApiHeart[] emptyArray() {
      // Lazily initializes the empty array
      if (_emptyArray == null) {
        synchronized (
            com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
          if (_emptyArray == null) {
            _emptyArray = new ApiHeart[0];
          }
        }
      }
      return _emptyArray;
    }
    
    public ApiHeart() {
      clear();
    }
    
    public ApiHeart clear() {
      cachedSize = -1;
      return this;
    }
    
    @Override
    public ApiHeart mergeFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            return this;
          default: {
            if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
              return this;
            }
            break;
          }
        }
      }
    }
    
    public static ApiHeart parseFrom(byte[] data)
        throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
      return com.google.protobuf.nano.MessageNano.mergeFrom(new ApiHeart(), data);
    }
    
    public static ApiHeart parseFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      return new ApiHeart().mergeFrom(input);
    }
  }
  
  public static final class ApiCancel extends
      com.google.protobuf.nano.MessageNano {
    
    private static volatile ApiCancel[] _emptyArray;
    public static ApiCancel[] emptyArray() {
      // Lazily initializes the empty array
      if (_emptyArray == null) {
        synchronized (
            com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
          if (_emptyArray == null) {
            _emptyArray = new ApiCancel[0];
          }
        }
      }
      return _emptyArray;
    }
    
    public ApiCancel() {
      clear();
    }
    
    public ApiCancel clear() {
      cachedSize = -1;
      return this;
    }
    
    @Override
    public ApiCancel mergeFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            return this;
          default: {
            if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
              return this;
            }
            break;
          }
        }
      }
    }
    
    public static ApiCancel parseFrom(byte[] data)
        throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
      return com.google.protobuf.nano.MessageNano.mergeFrom(new ApiCancel(), data);
    }
    
    public static ApiCancel parseFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      return new ApiCancel().mergeFrom(input);
    }
  }
  
  public static final class ApiThirdData extends
      com.google.protobuf.nano.MessageNano {
    
    private static volatile ApiThirdData[] _emptyArray;
    public static ApiThirdData[] emptyArray() {
      // Lazily initializes the empty array
      if (_emptyArray == null) {
        synchronized (
            com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
          if (_emptyArray == null) {
            _emptyArray = new ApiThirdData[0];
          }
        }
      }
      return _emptyArray;
    }
    
    // required uint32 len = 1;
    public Integer len;
    
    // required bytes third_data = 2;
    public byte[] thirdData;
    
    public ApiThirdData() {
      clear();
    }
    
    public ApiThirdData clear() {
      len = null;
      thirdData = null;
      cachedSize = -1;
      return this;
    }
    
    @Override
    public void writeTo(com.google.protobuf.nano.CodedOutputByteBufferNano output)
        throws java.io.IOException {
      output.writeUInt32(1, this.len);
      output.writeBytes(2, this.thirdData);
      super.writeTo(output);
    }
    
    @Override
    protected int computeSerializedSize() {
      int size = super.computeSerializedSize();
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeUInt32Size(1, this.len);
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeBytesSize(2, this.thirdData);
      return size;
    }
    
    @Override
    public ApiThirdData mergeFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            return this;
          default: {
            if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
              return this;
            }
            break;
          }
          case 8: {
            this.len = input.readUInt32();
            break;
          }
          case 18: {
            this.thirdData = input.readBytes();
            break;
          }
        }
      }
    }
    
    public static ApiThirdData parseFrom(byte[] data)
        throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
      return com.google.protobuf.nano.MessageNano.mergeFrom(new ApiThirdData(), data);
    }
    
    public static ApiThirdData parseFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      return new ApiThirdData().mergeFrom(input);
    }
  }
  
  public static final class APIRequest extends
      com.google.protobuf.nano.MessageNano {
    
    private static volatile APIRequest[] _emptyArray;
    public static APIRequest[] emptyArray() {
      // Lazily initializes the empty array
      if (_emptyArray == null) {
        synchronized (
            com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
          if (_emptyArray == null) {
            _emptyArray = new APIRequest[0];
          }
        }
      }
      return _emptyArray;
    }
    
    // required .speech.APIReqType api_req_type = 1;
    public Integer apiReqType;
    
    // optional .speech.ApiParam param = 2;
    public ApiParam param;
    
    // optional .speech.ApiData data = 3;
    public ApiData data;
    
    // optional .speech.ApiLast last = 4;
    public ApiLast last;
    
    // optional .speech.ApiCancel cancel = 5;
    public ApiCancel cancel;
    
    // optional .speech.ApiThirdData third_data = 6;
    public ApiThirdData thirdData;
    
    public APIRequest() {
      clear();
    }
    
    public APIRequest clear() {
      apiReqType = null;
      param = null;
      data = null;
      last = null;
      cancel = null;
      thirdData = null;
      cachedSize = -1;
      return this;
    }
    
    @Override
    public void writeTo(com.google.protobuf.nano.CodedOutputByteBufferNano output)
        throws java.io.IOException {
      output.writeInt32(1, this.apiReqType);
      if (this.param != null) {
        output.writeMessage(2, this.param);
      }
      if (this.data != null) {
        output.writeMessage(3, this.data);
      }
      if (this.last != null) {
        output.writeMessage(4, this.last);
      }
      if (this.cancel != null) {
        output.writeMessage(5, this.cancel);
      }
      if (this.thirdData != null) {
        output.writeMessage(6, this.thirdData);
      }
      super.writeTo(output);
    }
    
    @Override
    protected int computeSerializedSize() {
      int size = super.computeSerializedSize();
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
        .computeInt32Size(1, this.apiReqType);
      if (this.param != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeMessageSize(2, this.param);
      }
      if (this.data != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeMessageSize(3, this.data);
      }
      if (this.last != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeMessageSize(4, this.last);
      }
      if (this.cancel != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeMessageSize(5, this.cancel);
      }
      if (this.thirdData != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeMessageSize(6, this.thirdData);
      }
      return size;
    }
    
    @Override
    public APIRequest mergeFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            return this;
          default: {
            if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
              return this;
            }
            break;
          }
          case 8: {
            int value = input.readInt32();
            switch (value) {
              case StreamApi.API_REQ_TYPE_PARAM:
              case StreamApi.API_REQ_TYPE_DATA:
              case StreamApi.API_REQ_TYPE_LAST:
              case StreamApi.API_REQ_TYPE_CANCEL:
              case StreamApi.API_REQ_TYPE_THIRD_DATA:
                this.apiReqType = value;
                break;
            }
            break;
          }
          case 18: {
            if (this.param == null) {
              this.param = new ApiParam();
            }
            input.readMessage(this.param);
            break;
          }
          case 26: {
            if (this.data == null) {
              this.data = new ApiData();
            }
            input.readMessage(this.data);
            break;
          }
          case 34: {
            if (this.last == null) {
              this.last = new ApiLast();
            }
            input.readMessage(this.last);
            break;
          }
          case 42: {
            if (this.cancel == null) {
              this.cancel = new ApiCancel();
            }
            input.readMessage(this.cancel);
            break;
          }
          case 50: {
            if (this.thirdData == null) {
              this.thirdData = new ApiThirdData();
            }
            input.readMessage(this.thirdData);
            break;
          }
        }
      }
    }
    
    public static APIRequest parseFrom(byte[] data)
        throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
      return com.google.protobuf.nano.MessageNano.mergeFrom(new APIRequest(), data);
    }
    
    public static APIRequest parseFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      return new APIRequest().mergeFrom(input);
    }
  }
  
  public static final class ASRResult extends
      com.google.protobuf.nano.MessageNano {
    
    private static volatile ASRResult[] _emptyArray;
    public static ASRResult[] emptyArray() {
      // Lazily initializes the empty array
      if (_emptyArray == null) {
        synchronized (
            com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
          if (_emptyArray == null) {
            _emptyArray = new ASRResult[0];
          }
        }
      }
      return _emptyArray;
    }
    
    // repeated string word = 1;
    public String[] word;
    
    // repeated string uncertain_word = 2;
    public String[] uncertainWord;
    
    public ASRResult() {
      clear();
    }
    
    public ASRResult clear() {
      word = com.google.protobuf.nano.WireFormatNano.EMPTY_STRING_ARRAY;
      uncertainWord = com.google.protobuf.nano.WireFormatNano.EMPTY_STRING_ARRAY;
      cachedSize = -1;
      return this;
    }
    
    @Override
    public void writeTo(com.google.protobuf.nano.CodedOutputByteBufferNano output)
        throws java.io.IOException {
      if (this.word != null && this.word.length > 0) {
        for (int i = 0; i < this.word.length; i++) {
          String element = this.word[i];
          if (element != null) {
            output.writeString(1, element);
          }
        }
      }
      if (this.uncertainWord != null && this.uncertainWord.length > 0) {
        for (int i = 0; i < this.uncertainWord.length; i++) {
          String element = this.uncertainWord[i];
          if (element != null) {
            output.writeString(2, element);
          }
        }
      }
      super.writeTo(output);
    }
    
    @Override
    protected int computeSerializedSize() {
      int size = super.computeSerializedSize();
      if (this.word != null && this.word.length > 0) {
        int dataCount = 0;
        int dataSize = 0;
        for (int i = 0; i < this.word.length; i++) {
          String element = this.word[i];
          if (element != null) {
            dataCount++;
            dataSize += com.google.protobuf.nano.CodedOutputByteBufferNano
                .computeStringSizeNoTag(element);
          }
        }
        size += dataSize;
        size += 1 * dataCount;
      }
      if (this.uncertainWord != null && this.uncertainWord.length > 0) {
        int dataCount = 0;
        int dataSize = 0;
        for (int i = 0; i < this.uncertainWord.length; i++) {
          String element = this.uncertainWord[i];
          if (element != null) {
            dataCount++;
            dataSize += com.google.protobuf.nano.CodedOutputByteBufferNano
                .computeStringSizeNoTag(element);
          }
        }
        size += dataSize;
        size += 1 * dataCount;
      }
      return size;
    }
    
    @Override
    public ASRResult mergeFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            return this;
          default: {
            if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
              return this;
            }
            break;
          }
          case 10: {
            int arrayLength = com.google.protobuf.nano.WireFormatNano
                .getRepeatedFieldArrayLength(input, 10);
            int i = this.word == null ? 0 : this.word.length;
            String[] newArray = new String[i + arrayLength];
            if (i != 0) {
              System.arraycopy(this.word, 0, newArray, 0, i);
            }
            for (; i < newArray.length - 1; i++) {
              newArray[i] = input.readString();
              input.readTag();
            }
            // Last one without readTag.
            newArray[i] = input.readString();
            this.word = newArray;
            break;
          }
          case 18: {
            int arrayLength = com.google.protobuf.nano.WireFormatNano
                .getRepeatedFieldArrayLength(input, 18);
            int i = this.uncertainWord == null ? 0 : this.uncertainWord.length;
            String[] newArray = new String[i + arrayLength];
            if (i != 0) {
              System.arraycopy(this.uncertainWord, 0, newArray, 0, i);
            }
            for (; i < newArray.length - 1; i++) {
              newArray[i] = input.readString();
              input.readTag();
            }
            // Last one without readTag.
            newArray[i] = input.readString();
            this.uncertainWord = newArray;
            break;
          }
        }
      }
    }
    
    public static ASRResult parseFrom(byte[] data)
        throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
      return com.google.protobuf.nano.MessageNano.mergeFrom(new ASRResult(), data);
    }
    
    public static ASRResult parseFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      return new ASRResult().mergeFrom(input);
    }
  }
  
  public static final class APIResponse extends
      com.google.protobuf.nano.MessageNano {
    
    private static volatile APIResponse[] _emptyArray;
    public static APIResponse[] emptyArray() {
      // Lazily initializes the empty array
      if (_emptyArray == null) {
        synchronized (
            com.google.protobuf.nano.InternalNano.LAZY_INIT_LOCK) {
          if (_emptyArray == null) {
            _emptyArray = new APIResponse[0];
          }
        }
      }
      return _emptyArray;
    }
    
    // required .speech.APIRespType type = 1;
    public Integer type;
    
    // required string id = 2;
    public String id;
    
    // required int32 err_no = 3;
    public Integer errNo;
    
    // optional string err_msg = 4;
    public String errMsg;
    
    // optional .speech.ASRResult result = 5;
    public ASRResult result;
    
    // optional .speech.ApiThirdData third_data = 6;
    public ApiThirdData thirdData;
    
    // optional .speech.ApiHeart heart = 7;
    public ApiHeart heart;
    
    // optional .speech.ApiLast last = 8;
    public ApiLast last;
    
    public APIResponse() {
      clear();
    }
    
    public APIResponse clear() {
      type = null;
      id = null;
      errNo = null;
      errMsg = null;
      result = null;
      thirdData = null;
      heart = null;
      last = null;
      cachedSize = -1;
      return this;
    }
    
    @Override
    public void writeTo(com.google.protobuf.nano.CodedOutputByteBufferNano output)
        throws java.io.IOException {
      output.writeInt32(1, this.type);
      output.writeString(2, this.id);
      output.writeInt32(3, this.errNo);
      if (this.errMsg != null) {
        output.writeString(4, this.errMsg);
      }
      if (this.result != null) {
        output.writeMessage(5, this.result);
      }
      if (this.thirdData != null) {
        output.writeMessage(6, this.thirdData);
      }
      if (this.heart != null) {
        output.writeMessage(7, this.heart);
      }
      if (this.last != null) {
        output.writeMessage(8, this.last);
      }
      super.writeTo(output);
    }
    
    @Override
    protected int computeSerializedSize() {
      int size = super.computeSerializedSize();
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
        .computeInt32Size(1, this.type);
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeStringSize(2, this.id);
      size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeInt32Size(3, this.errNo);
      if (this.errMsg != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
            .computeStringSize(4, this.errMsg);
      }
      if (this.result != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeMessageSize(5, this.result);
      }
      if (this.thirdData != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeMessageSize(6, this.thirdData);
      }
      if (this.heart != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeMessageSize(7, this.heart);
      }
      if (this.last != null) {
        size += com.google.protobuf.nano.CodedOutputByteBufferNano
          .computeMessageSize(8, this.last);
      }
      return size;
    }
    
    @Override
    public APIResponse mergeFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            return this;
          default: {
            if (!com.google.protobuf.nano.WireFormatNano.parseUnknownField(input, tag)) {
              return this;
            }
            break;
          }
          case 8: {
            int value = input.readInt32();
            switch (value) {
              case StreamApi.API_RESP_TYPE_MIDDLE:
              case StreamApi.API_RESP_TYPE_RES:
              case StreamApi.API_RESP_TYPE_THIRD:
              case StreamApi.API_RESP_TYPE_HEART:
              case StreamApi.API_RESP_TYPE_LAST:
                this.type = value;
                break;
            }
            break;
          }
          case 18: {
            this.id = input.readString();
            break;
          }
          case 24: {
            this.errNo = input.readInt32();
            break;
          }
          case 34: {
            this.errMsg = input.readString();
            break;
          }
          case 42: {
            if (this.result == null) {
              this.result = new ASRResult();
            }
            input.readMessage(this.result);
            break;
          }
          case 50: {
            if (this.thirdData == null) {
              this.thirdData = new ApiThirdData();
            }
            input.readMessage(this.thirdData);
            break;
          }
          case 58: {
            if (this.heart == null) {
              this.heart = new ApiHeart();
            }
            input.readMessage(this.heart);
            break;
          }
          case 66: {
            if (this.last == null) {
              this.last = new ApiLast();
            }
            input.readMessage(this.last);
            break;
          }
        }
      }
    }
    
    public static APIResponse parseFrom(byte[] data)
        throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
      return com.google.protobuf.nano.MessageNano.mergeFrom(new APIResponse(), data);
    }
    
    public static APIResponse parseFrom(
            com.google.protobuf.nano.CodedInputByteBufferNano input)
        throws java.io.IOException {
      return new APIResponse().mergeFrom(input);
    }
  }
}
