<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>文件列表</title>
	<%@include file="../common/common.jsp" %>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/portal/css/element-ui.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/portal/css/file.css?v=20190723">
</head>

<body>
	<article class="file-layout" id="app">
		<el-row :gutter="20">
			<el-col :span="4">
				<div class="grid-content" id="tree">
					<div class="header">
						<span>文件类型</span>
					</div>
					<el-tree 
					:data="fileType" 
					:props="defaultProps"
					 @node-click="handleNodeClick"
					 ref="trees"
					 node-key="id"
					 :default-expanded-keys="[fileType.length>0?fileType[0].id:'1']"
					 :expand-on-click-node="false"
					></el-tree>
				</div>
			</el-col>
			<el-col :span="15">
				<el-form :inline="true" :model="form" class="demo-form-inline clearfix">
					<el-form-item class="select-type" label="受众群体:">
						<el-select v-model="form.identityType " placeholder="请选择">
							<el-option
								v-for="item in form.options"
								:key="item.identityType "
								:value="item.identityType ">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item class="search-ipt">
						<el-input v-model="form.fileName" maxlength="255" @keyup.enter.native="onSubmit" clearable placeholder="请输入文件名称搜索"></el-input>
					</el-form-item>
					<el-form-item class="search-btn">
						<el-button type="primary" @click="onSubmit">搜&nbsp;&nbsp;索</el-button>
					</el-form-item>
				</el-form>
				<div class="grid-content file-box">
					<div class="header">
						<p v-if="!isSubmit">
							<svg class="iconp" aria-hidden="true">
								<use xlink:href="#icon-icon-test"></use>
							</svg>
							<span v-text="title"></span>
						</p>
						<p v-else>
							共搜索到<span class="red" v-text="total"></span>个相关文件
						</p>
					</div>
					<ul class="file-list" id="fileList" v-loading="loading">
						<li  v-for="(item,index) in fileList" :key="index">
							<div class="file-name clearfix main-color">
								<div class="file-fl" :class="{'view-flag':item.hasRead==1}">
									<span v-if="item.edition" v-text="'【'+item.edition+'】'"></span>
									<span v-if="item.fileName" @click="viewFile(item,1)" :title="item.fileName">
										<i v-if="isSubmit" v-html="searchResult(item.fileName,1)"></i>
										<i v-else v-text="item.fileName"></i>
									</span>
								</div>
								<span class="file-fl collect" @click="collect(item)" style="width:5%;text-align:right" :title="item.collectFlag==1?'取消收藏':'收藏文件'">
									<svg class="iconp" v-if="item.collectFlag==0"  aria-hidden="true">
										<use xlink:href="#icon-shoucangkong"></use>
									</svg>
									<svg class="iconp"  v-if="item.collectFlag==1"  aria-hidden="true">
										<use xlink:href="#icon-shoucang2"></use>
									</svg>
								</span>
							</div>
							<div class="file-info">
								<span v-if="item.typeName" v-html="searchResult(item.typeName,2)"></span>
								<span v-if="item.fileTime" v-text="'发布时间：'+item.fileTime"></span>
								<span v-if="item.startTime" v-text="'生效时间：'+item.startTime"></span>
								<span class="fr" v-if="item.browseNumber||item.browseNumber==0" v-text="'阅读量：'+item.browseNumber"></span>
							</div>
						</li>
						<div class="c-99" id="empty" style="display: none">
							<p><img src="${pageContext.request.contextPath}/scripts/portal/images/bitmap.png" alt=""></p>
							暂无文件数据~
						</div>
					</ul>
					<div class="block">
						<el-pagination
							@size-change="handleSizeChange"
							@current-change="handleCurrentChange"
							:current-page="page"
							:page-sizes="[10, 20, 50, 100]"
							:page-size="10"
							layout="total, sizes, prev, pager, next, jumper"
							:total="total">
						</el-pagination>
					</div>
				</div>
			</el-col>
			<el-col :span="5">
				<div class="grid-content file-box collect-list">
					<div class="header">
						<svg class="iconp"  aria-hidden="true">
							<use xlink:href="#icon-shoucang2"></use>
						</svg>
						<span>我的收藏</span>
					</div>
					<ul class="file-list" v-loading="collectLoading">
						<el-carousel :interval="0" height="410px" ref="carousel" arrow="never">
							<el-carousel-item v-if="collectList.length>0"> 
								<li  v-for="(item,index) in collectList" :key="index">
									<!--  已失效-->
									<div class="file-name clearfix main-color" :class="{'delect-flag':item.deleteFlag==1}" >
										<div class="spot"></div>
										<div class="file-fl">
											<span v-if="item.title"  @click="viewFile(item,2)" :title="item.title" v-text="item.title"></span>
										</div>
										<span class="file-fl collect" @click="collectCancel(item)" title="取消收藏">
											<svg class="iconp"  aria-hidden="true">
												<use xlink:href="#icon-shoucang2"></use>
											</svg>
										</span>
									</div>
								</li>
							</el-carousel-item>
							<div class="c-99" v-if="Array.isArray(collectList)&&collectList.length==0" style="margin-top:152px;height: auto;
							line-height: inherit;" >
								<p><img style="width:80px;" src="${pageContext.request.contextPath}/scripts/portal/images/bitmap.png" alt=""></p>
								暂无收藏信息~
							</div>
						</el-carousel>
					</ul>
				</div>
				<p class="page clearfix" v-if="collectList.length>0">
					<span class="fl"  v-text="currentPage+'/'+collectPages"></span>
					<span class="fr">
						<svg class="iconp" :class="{'active':currentPage===1}" @click="carouselSwitch(1)"  aria-hidden="true">
							<use xlink:href="#icon-zuojiantou1"></use>
						</svg>
						<svg class="iconp" :class="{'active':currentPage==collectPages}" @click="carouselSwitch(2)"  aria-hidden="true">
							<use xlink:href="#icon-youjiantou1"></use>
						</svg>
					</span>
				</p>
			</el-col>
		</el-row>
	</article>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/portal/vue-2.6.10.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/portal/element-ui.js"></script>
	<script src="${pageContext.request.contextPath}/scripts/portal/portal-iconfont.js"></script>
	<script type="text/javascript">
		//实例化vue
		var vm = new Vue({
			el: "#app",
			data: {
				fileType:"", 
				defaultProps: {
					children: 'fileTypeVoList',
					label: 'twoTypeName'
				},
				form:{
					options:[],
					fileName:"",
					identityType :"", //受众群体
					twoTypeName:"最新文件",  //文件类型
					typeId:"",//文件类型id
				},
				fileList:"", //文件列表
				loading:false, //文件列表loading
				page:1, //文件列表页码
				total:0, //文件列表总条数
				rows:10, //文件列表条数
				collectList:"",//收藏列表
				currentPage:1,//收藏列表页数 
				pageSize:10,  //收藏列表条数
				collectLoading:false, //收藏列表loading
				collectPages:"", //收藏列表总页码
				collectTotal:"",//收藏列表总条数
				title:"最新文件",//tree标题
				isSubmit:false,//是否点击搜索按钮
				fileNameSure:"",//搜索fileName值
			},
			mounted() {
				this.init();
			},
			methods: {
				init() {
					this.getUserViewScope();
					this.getMyCollectFile();
					let ht=$(window).height();
					$("#fileList").height(ht-200+'px');
					$("#empty").css("marginTop",(ht-200)/2-58+'px');
					$("#empty").show();
					$("#tree").height(ht-40+'px');
				},
				onSubmit() {
					this.getFileType();
					this.isSubmit=true;
					this.form.twoTypeName="最新文件";
					this.form.typeId="";
					this.fileNameSure=this.form.fileName;
					$("#empty").hide();
				},
				//el-tree 树节点点击事件
				handleNodeClick(obj,node){
					this.form.twoTypeName=obj.twoTypeName;
					this.form.twoTypeName=this.title=obj.twoTypeName=="公司文件"?"最新文件":obj.twoTypeName;
					this.form.typeId=obj.id;
					this.page=1;
					this.form.fileName="";
					this.fileNameSure="";
					this.isSubmit=false;
					$("#empty").hide();
					this.getNewFileList();
				},
				//页码大小事件
				handleSizeChange(val) {
					this.page=1;
					this.rows=val;
					this.getNewFileList();
				},
				//当前页变动事件
				handleCurrentChange(val) {
					this.page=val;
					this.getNewFileList();
				},
				//搜索结果名称高亮
				searchResult(val,idx){
					//idx 1为标题，2为分类
					if(idx==1){
						let str=this.fileNameSure.trim();
						if(val.indexOf(str)!=-1){
							let strHtml=val.substring(0,val.indexOf(str))+"<em class='red'>"+str+"</em>"+val.substring(val.indexOf(str)+str.length);
							return strHtml;
						}else{
							return val;
						}
					}else{
						if(val.indexOf(this.title)!=-1){
							let strHtml="<em class='red'>"+val+"</em>";
							return strHtml;
						}else{
							return val;
						}
					}
				},
				//获取文件类型接口
				getFileType() {
					let that=this;
					let prams={
						oneTypeName :this.form.identityType 
					}
					$.ajax({
						url: '${pageContext.request.contextPath}/file/getFileType.do',
						type: "GET",
						data: prams,
						success: function (data) {
							if(Array.isArray(data)&&data.length>0){
								that.fileType=data;
								that.page=1;
								that.getNewFileList();
							}else{
								that.fileType=[]
							}
						}
					});
				},
				//受众群众接口
				getUserViewScope(){
					let that=this;
					$.ajax({
						url: '${pageContext.request.contextPath}/file/getUserViewScope.do',
						type: "GET",
						data: {},
						success: function (data) {
							if(Array.isArray(data)&&data.length>0){
								let obj={},arr=[];
								data.forEach(res=>{
									obj={};
									obj.identityType=res;
									arr.push(obj);
								})
								that.form.identityType =arr[0].identityType ;
								that.form.options=arr;
								that.getFileType();
							}
						}
					});
				},
				//文件列表
				getNewFileList(){
					let that=this;
					that.loading=true;
					let params={};
					params.identityType =this.form.identityType ;
					params.fileName=this.form.fileName;
					params.twoTypeName=this.form.twoTypeName;
					params.typeId=this.form.typeId;
					params.rows=this.rows;
					params.page=this.page;
					$.ajax({
						url: '${pageContext.request.contextPath}/file/getNewFileList.do',
						type: "GET",
						data: params,
						success: function (data) {
							that.loading=false;
							//collectFlag  0：未收藏 1：收藏 
							//hasRead   0：未读，1：己读
							if(data.success){
								if(Array.isArray(data.data)&&data.data.length>0){
									data.data.forEach(res=>{
										res.fileTime=that.formatDate(res.fileTime);
										res.startTime=that.formatDate(res.startTime);
										let str="";
										// 总部取headquartersName，
										// 省区去provinceName，
										// 网点取dotName，
										// 快递员取courierName
										if(that.form.identityType=="总部"){
											str=res.headquartersName
										}else if(that.form.identityType=="省区"){
											str=res.provinceName
										}else if(that.form.identityType=="网点"){
											str=res.dotName
										}else if(that.form.identityType=="快递员"){
											str=res.courierName
										}
										that.$set(res,'typeName',str);
									})
									that.fileList = data.data;
									that.total=data.total;
									$("#empty").hide();
								}else{
									that.fileList=[];
									that.total=0;
									$("#empty").show();
								}
							}else{
								that.fileList=[];
								that.total=0;
								$("#empty").show();
							}
						},
						error:function(err){
							that.loading=false;
							$("#empty").show();
						}
					});
				},
				//收藏列表
				getMyCollectFile(){
					this.collectLoading=true;
					let that=this;
					let prams={
						currentPage:this.currentPage,//收藏列表页数 
						pageSize:this.pageSize,  //收藏列表条数
					}
					$.ajax({
						url: '${pageContext.request.contextPath}/file/getMyCollectFile.do',
						type: "GET",
						data: prams,
						success: function (data) {
							that.collectLoading=false;
							if(Array.isArray(data.data)&&data.data.length>0){
								that.collectList = data.data;
								//that.collectPage=Math.ceil(data.length/5);
								//that.carouselChange(0);
							}else{
								that.collectList=[];
							}
							that.collectTotal=data.total
							that.collectPages=data.totalPages;
						},
						error:function(){
							that.collectLoading=false;
						}
					});
				},
				//文件列表 收藏
				collect(item){
					//collectFlag  1：收藏 0：未收藏
					if(item.collectFlag==0){
						this.addCollect(item,res=>{
							item.collectFlag=1; //收藏
							this.currentPage=1;
							this.pageSize=10;
							this.getMyCollectFile();
						});
					}else{
						this.cancelCollect(item,1,res=>{
							item.collectFlag=0; //取消收藏
							this.currentPage=1;
							this.pageSize=10;
							this.getMyCollectFile();
						})
					}
				},
				//收藏列表 取消收藏
				collectCancel(item){
					this.cancelCollect(item,2,res=>{
						this.getMyCollectFile();
						this.fileList.forEach(val=>{
							if(item.primaryKey==val.id){ //通过ID判断
								val.collectFlag=0;
							}
						})
					})
				},
				//收藏
				addCollect(item,callback){
					let params={
						primaryKey:item.id,
						title:item.fileName,
						detailUrl:item.fileUrl
					}
					let that=this;
					$.ajax({
						url: '${pageContext.request.contextPath}/file/addCollect.do',
						type: "GET",
						data: params,
						success: function (data) {
							if(data.success){
								callback();
							}
						},
					});
				},
				//取消收藏
				cancelCollect(item,idx,callback){
					let params={
						primaryKey:item.id

					}
					if(idx==2){
						params.primaryKey=item.primaryKey
					}
					let that=this;
					$.ajax({
						url: '${pageContext.request.contextPath}/file/cancelCollect.do',
						type: "GET",
						data: params,
						success: function (data) {
							if(data.success){
								callback();
							}else{
								that.$message({
									showClose: true,
									message: data.errorText,
									type: 'error',
								});
							}
						},
					});
				},
				//日期格式化
				formatDate(val) {
					if (val) {
						let time = new Date(val);
						let y = time.getFullYear();
						let m = time.getMonth() + 1 >= 10 ? time.getMonth() + 1 : "0" + (time.getMonth() + 1);
						let d = time.getDate() >= 10 ? time.getDate() : "0" + time.getDate();
						let h = time.getHours() >= 10 ? time.getHours() : "0" + time.getHours();
						let n = time.getMinutes() >= 10 ? time.getMinutes() : "0" + time.getMinutes();
						let s = time.getSeconds() >= 10 ? time.getSeconds() : "0" + time.getSeconds();
						return y + "-" + m + "-" + d + " " + h + ":" + n + ":" + s;
					} else {
						return ""
					}
				},
				//预览附件
				viewFile(item,idx) {
					//1.已读，2.打开附件
					var urlHost = window.location.href;
					var index=urlHost.lastIndexOf("/omg-portal-main");
					var urlP = urlHost.substring(0,index);
					var pdfUrl = item.fileUrl ;
					if(idx==2){
						pdfUrl=item.detailUrl
					}
					if(item.deleteFlag==1&&idx==2){
						this.$message({
							showClose: true,
							message: "该文件已失效,无法查看!",
							type: 'error',
						});
						return false;
					}
					let strIndex = pdfUrl.lastIndexOf(".");
					let length=pdfUrl.length;
					let str=pdfUrl.substring(strIndex+1,length);
					str=str.toLowerCase();
					if(str!='pdf'){
						this.$message({
							showClose: true,
							message: "暂时只支持pdf格式的文件预览!",
							type: 'error',
						});
						return false;
					}
					var watermark = encodeURI(portal_global_currentUser.empCode + " " + portal_global_currentUser.empName);
					if(item.hasRead===0){
						this.updateBrowse(item)
					}
					var url = urlP + "${pageContext.request.contextPath}/scripts/portal/pdf/web/viewer.html?fileUrl="+pdfUrl+"&watermark=" + watermark ;
					openTabCrossDomain(null,{},{title:"文件预览",systemCode:"PORTAL",url:url});
					
				},
				//阅读
				updateBrowse(item){
					let params={
						primaryKey:item.id
					}
					let that=this;
					$.ajax({
						url: '${pageContext.request.contextPath}/file/updateBrowse.do',
						type: "GET",
						data: params,
						success: function (data) {
							item.hasRead=1; //已读
						},
					});
				},
				carouselSwitch(idx){
					//idx  1为左 2为右
					//左键 总页码大于1，可以切换至上一页   右键 当前索引不在最后一页，可切换至下一页。
					if(idx==1){
						if(this.collectPages!=1&&this.currentPage!=1){
							this.currentPage--;
						}else{
							return false;
						}
					}else{
						if(this.currentPage==this.collectPages){
							return false
						}else{
							this.currentPage++;
						}
					}
					this.getMyCollectFile();	
				}
			}
		})
	</script>
</body>

</html>