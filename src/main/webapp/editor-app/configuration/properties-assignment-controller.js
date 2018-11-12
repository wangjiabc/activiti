/*
 * Activiti Modeler component part of the Activiti project
 * Copyright 2005-2014 Alfresco Software, Ltd. All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

/*
 * Assignment
 */
var KisBpmAssignmentCtrl = [ '$scope', '$modal', '$http', function($scope, $modal, $http) {

	var opts = {
	    template:  'editor-app/configuration/properties/assignment-popup.html?version=' + Date.now(),
	    scope: $scope
	};
	
	// Open the dialog
	$modal(opts);
	
}];

var KisBpmAssignmentPopupCtrl = [ '$scope', '$modal', function($scope, $modal) {
    	
    // Put json representing assignment on scope
    if ($scope.property.value !== undefined && $scope.property.value !== null
        && $scope.property.value.assignment !== undefined
        && $scope.property.value.assignment !== null) 
    {
        $scope.assignment = $scope.property.value.assignment;
    } else {
        $scope.assignment = {};
    }

    if ($scope.assignment.candidateUsers == undefined || $scope.assignment.candidateUsers.length == 0)
    {
    	$scope.assignment.candidateUsers = [{value: ''}];
    }
    
    // Click handler for + button after enum value
    var userValueIndex = 1;
    $scope.addCandidateUserValue = function(index) {
        $scope.assignment.candidateUsers.splice(index + 1, 0, {value: 'value ' + userValueIndex++});
    };

    // Click handler for - button after enum value
    $scope.removeCandidateUserValue = function(index) {
        $scope.assignment.candidateUsers.splice(index, 1);
    };
    
    if ($scope.assignment.candidateGroups == undefined || $scope.assignment.candidateGroups.length == 0)
    {
    	$scope.assignment.candidateGroups = [{value: ''}];
    }
    
    var groupValueIndex = 1;
    $scope.addCandidateGroupValue = function(index) {
        $scope.assignment.candidateGroups.splice(index + 1, 0, {value: 'value ' + groupValueIndex++});
    };

    // Click handler for - button after enum value
    $scope.removeCandidateGroupValue = function(index) {
        $scope.assignment.candidateGroups.splice(index, 1);
    };

    //Open the dialog to select users
    $scope.choseAssignment = function(flag) {
    	
    	var opts = {
		    template:  'editor-app/configuration/properties/assignment-popup-popup.html?version=' + Date.now(),
		    scope: $scope
		};
		$scope.choseAssignmentFlag = flag;
		// Open the dialog
		$modal(opts);
    }
    
  //Open the dialog to select candidateGroups
    $scope.choseCandidateUser = function(){
    	var opts = {
		    template:  'editor-app/configuration/properties/assignment-candidateUser.html?version=' + Date.now(),
		    scope: $scope
		};
		// Open the dialog
		$modal(opts);
    }
    
    //Open the dialog to select candidateGroups
    $scope.choseCandidateGroups = function(){
    	var opts = {
		    template:  'editor-app/configuration/properties/assignment-candidateGroup.html?version=' + Date.now(),
		    scope: $scope
		};
		// Open the dialog
		$modal(opts);
    }
    
    $scope.save = function() {

        $scope.property.value = {};
        handleAssignmentInput($scope);
        $scope.property.value.assignment = $scope.assignment;
        
        $scope.updatePropertyInModel($scope.property);
        $scope.close();
    };

    // Close button handler
    $scope.close = function() {
    	handleAssignmentInput($scope);
    	$scope.property.mode = 'read';
    	$scope.$hide();
    };
    
    var handleAssignmentInput = function($scope) {
    	if ($scope.assignment.candidateUsers)
    	{
	    	var emptyUsers = true;
	    	var toRemoveIndexes = [];
	        for (var i = 0; i < $scope.assignment.candidateUsers.length; i++)
	        {
	        	if ($scope.assignment.candidateUsers[i].value != '')
	        	{
	        		emptyUsers = false;
	        	}
	        	else
	        	{
	        		toRemoveIndexes[toRemoveIndexes.length] = i;
	        	}
	        }
	        
	        for (var i = 0; i < toRemoveIndexes.length; i++)
	        {
	        	$scope.assignment.candidateUsers.splice(toRemoveIndexes[i], 1);
	        }
	        
	        if (emptyUsers)
	        {
	        	$scope.assignment.candidateUsers = undefined;
	        }
    	}
        
    	if ($scope.assignment.candidateGroups)
    	{
	        var emptyGroups = true;
	        var toRemoveIndexes = [];
	        for (var i = 0; i < $scope.assignment.candidateGroups.length; i++)
	        {
	        	if ($scope.assignment.candidateGroups[i].value != '')
	        	{
	        		emptyGroups = false;
	        	}
	        	else
	        	{
	        		toRemoveIndexes[toRemoveIndexes.length] = i;
	        	}
	        }
	        
	        for (var i = 0; i < toRemoveIndexes.length; i++)
	        {
	        	$scope.assignment.candidateGroups.splice(toRemoveIndexes[i], 1);
	        }
	        
	        if (emptyGroups)
	        {
	        	$scope.assignment.candidateGroups = undefined;
	        }
    	}
    };
    
    //因新打开的界面上选定的数据要传输到当前modal中，所以使用此方式，这是angular.js中不同控制器之间传输数据的方式
    $scope.$on('choseAssigneeStr', function(event,data){
	      $scope.assignment.assignee = data;
	});
    $scope.$on('choseCandidateUserStr', function(event,data){
	      $scope.assignment.candidateUsers[0].value = data;
	});	
    
	$scope.$on('choseCandidateGroupsStr', function(event,data){
		$scope.assignment.candidateGroups[0].value = data;
	});
}];

var KisBpmChoseAssignmentCtrl = ['$scope', '$http', function($scope, $http) {
	//初始化左边菜单栏数据，并触发第一个菜单的点击事件
	var roles = [];
	var initId;
	$scope.getAllRoles = function (successCallback) {
	    $http({    
	    	method: 'get',
	        headers: {'Accept': 'application/json',
	                  'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
	        url: '/activiti/user/get.do'})
	
	        .success(function (data, status, headers, config) {
	        	var obj = data;
	        	for (var i=0; i<obj.length; i++) {
	        		if (i==0) {
	        			initId = obj[i].id + "";
	        			$scope.getAllAccountByRole(initId);
	        		}
	        		roles.push({id:obj[i].id,name:obj[i].name});
	        	}
	        	$scope.roles = roles;
	        })
	        .error(function (data, status, headers, config) {
	        });
	};
	$scope.getAllRoles(function(){});
	
	//模态框左侧组的点击事件：根据所点击的组获取当前组的所有用户
	 $scope.getAllAccountByRole = function(value) {
    	$http({    
	    	method: 'get',
	        headers: {'Accept': 'application/json',
	                  'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
	        url: '/activiti/user/get.do'})
	
	        .success(function (data, status, headers, config) {
	        	//封装数据
	        	var obj = data;
	  		    if (data != null) {
	  		    	var accounts = [];
	  		    	for (var i=0; i<obj.length; i++) {
	  		    		accounts.push({id:obj[i].id, code : obj[i].description, name : obj[i].name, index:i});
		        	}
	  		    	console.log(accounts);
	  		    	$scope.accounts=accounts;
	  		    }
	        })
	        .error(function (data, status, headers, config) {
	        });
    };
	
	// Close button handler
    $scope.close = function() {
    	$scope.$hide();
    };
    $scope.formData = {};
    $scope.candidateUser={};
    
    //Save Data
    $scope.save1 = function() {
    	var choseAssignee = $scope.accounts;
    	console.log(choseAssignee);
    	var choseAssigneeStr = "";
    	for (var i=0;i<choseAssignee.length; i++) {
    		if (choseAssignee[i].selected) {
    			choseAssigneeStr += choseAssignee[i].id + ",";
			}
    	}
    	choseAssigneeStr = choseAssigneeStr.substring(0,choseAssigneeStr.length-1);
    	console.log(choseAssigneeStr);
		$scope.$emit('choseAssigneeStr', choseAssigneeStr);
		$scope.close();
    };
    $scope.selectAll = function($event) {
    	var checkbox = $event.target;
    	var choseAssignees = $scope.accounts;
    	for (var i=0;i<choseAssignees.length; i++) {
    		if (checkbox.checked) {
    			choseAssignees[i].selected = true;
        	} else {
        		choseAssignees[i].selected = false;
        	}
    	}
    	$scope.accounts = choseAssignees;
    }
}];


var KisBpmCandidateUsersCtrl = ['$scope', '$http', function($scope, $http) {
	//初始化左边菜单栏数据，并触发第一个菜单的点击事件
	var roles = [];
	var initId;
	$scope.getAllRoles = function (successCallback) {
	    $http({    
	    	method: 'get',
	        headers: {'Accept': 'application/json',
	                  'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
	        url: '/activiti/user/get.do'})
	
	        .success(function (data, status, headers, config) {
	        	var obj = data;
	        	for (var i=0; i<obj.length; i++) {
	        		if (i==0) {
	        			initId = obj[i].id + "";
	        			$scope.getAllAccountByRole(initId);
	        		}
	        		roles.push({id:obj[i].id,name:obj[i].name});
	        	}
	        	$scope.roles = roles;
	        })
	        .error(function (data, status, headers, config) {
	        });
	};
	$scope.getAllRoles(function(){});
	
	//模态框左侧组的点击事件：根据所点击的组获取当前组的所有用户
	 $scope.getAllAccountByRole = function(value) {
    	$http({    
	    	method: 'get',
	        headers: {'Accept': 'application/json',
	                  'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
	        url: '/activiti/user/get.do'})
	
	        .success(function (data, status, headers, config) {
	        	//封装数据
	        	var obj = data;
	  		    if (data != null) {
	  		    	var accounts = [];
	  		    	for (var i=0; i<obj.length; i++) {
	  		    		accounts.push({id:obj[i].id, code : obj[i].description, name : obj[i].name, index:i});
		        	}
	  		    	console.log(accounts);
	  		    	$scope.accounts=accounts;
	  		    }
	        })
	        .error(function (data, status, headers, config) {
	        });
    };
	
	// Close button handler
    $scope.close = function() {
    	$scope.$hide();
    };
    $scope.formData = {};
    $scope.candidateUser={};
    
    //Save Data
    $scope.save = function() {
    	var choseCandidateUser = $scope.accounts;
    	console.log(choseCandidateUser);
    	var choseCandidateUserStr = "";
    	for (var i=0;i<choseCandidateUser.length; i++) {
    		if (choseCandidateUser[i].selected) {
    			choseCandidateUserStr  += choseCandidateUser[i].id + ",";
			}
    	}
    	choseCandidateUserStr = choseCandidateUserStr.substring(0,choseCandidateUserStr.length-1);
    	console.log(choseCandidateUserStr);
		$scope.$emit('choseCandidateUserStr', choseCandidateUserStr);
		$scope.close();
    };
    $scope.selectAll = function($event) {
    	var checkbox = $event.target;
    	var choseCandidateUsers = $scope.accounts;
    	for (var i=0;i<choseCandidateUsers.length; i++) {
    		if (checkbox.checked) {
    			choseCandidateUsers[i].selected = true;
        	} else {
        		choseCandidateUsers[i].selected = false;
        	}
    	}
    	$scope.accounts = choseCandidateUsers;
    }
}];

var KisBpmChoseCandidateGroupsCtrl = ['$scope', '$http', function($scope, $http) {
	
	var candidateGroups = [];
	$scope.getAllRoles = function (successCallback) {
	    $http({    
	    	method: 'get',
	        headers: {'Accept': 'application/json',
	                  'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
	        url: '/activiti/user/get.do'})
	
	        .success(function (data, status, headers, config) {
	        	var obj = data;
	        	for (var i=0; i<obj.length; i++) {
	        		candidateGroups.push({id:obj[i].id,name:obj[i].name,description:obj[i].description});
	        	}
	        	$scope.candidateGroups = candidateGroups;
	        })
	        .error(function (data, status, headers, config) {
	        });
	};
	$scope.getAllRoles(function() {
	});
	
	// Close button handler
    $scope.close = function() {
    	$scope.$hide();
    };
    
    $scope.save = function() {
    	var choseCandidateGroups = $scope.candidateGroups;
    	var choseCandidateGroupsStr = "";
    	for (var i=0;i<choseCandidateGroups.length; i++) {
    		if (choseCandidateGroups[i].selected) {
    			choseCandidateGroupsStr += choseCandidateGroups[i].id + ",";
			}
    	}
    	choseCandidateGroupsStr = choseCandidateGroupsStr.substring(0,choseCandidateGroupsStr.length-1);
		$scope.$emit('choseCandidateGroupsStr', choseCandidateGroupsStr);
		$scope.close();
    }
    
    $scope.selectAll = function($event) {
    	var checkbox = $event.target;
    	var candidateGroups = $scope.candidateGroups;
    	for (var i=0;i<candidateGroups.length; i++) {
    		if (checkbox.checked) {
    			candidateGroups[i].selected = true;
        	} else {
        		candidateGroups[i].selected = false;
        	}
    	}
    	$scope.candidateGroups = candidateGroups;
    }
}];
