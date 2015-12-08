var taskManagerModule = angular.module('taskManagerApp', [ 'ngAnimate' ]);

taskManagerModule.controller('categoryManagerController', function($scope,
		$http) {
	var urlBase = "";
	$http.defaults.headers.post["Content-Type"] = "application/json";

	function findParentCategories() {
		$http.get(urlBase + '/categories/search/findByParent?parent=').success(
				function(data) {
					if (data._embedded != undefined) {
						$scope.categories = data._embedded.categories;
					} else {
						$scope.categories = [];
					}
				});
	}

	findParentCategories();

	$scope.invertToggle = function() {
		$scope.categorySelected = !$scope.categorySelected;
	}

	function getChildCategories(category) {
		$http.get(
				urlBase + '/categories/search/findByParent?parent='
						+ category.categoryId).success(function(data) {
			if (data._embedded != undefined) {
				$scope.childCategories = data._embedded.categories;
			} else {
				$scope.childCategories = [];
			}
		});
	}

	$scope.selectParentCategory = function(category) {
		$scope.selectedCategories = [];
		$scope.selectCategory(category);
	}

	$scope.selectCategory = function(category) {
		$scope.selectedCategories.push(category);
		$scope.categorySelected = true;
		getChildCategories(category);
	}

	$scope.selectCategoryFromTop = function(category) {

		var i = $scope.selectedCategories.length;
		if ($scope.selectedCategories[i - 1] != category) {

			while ($scope.selectedCategories[i - 1] != category) {
				$scope.selectedCategories.splice(i - 1);
				i--;
			}

			getChildCategories(category);
		}
	}

	$scope.up = function() {

		length = $scope.selectedCategories.length;
		if (length < 1) {
			return;
		}
		if (length == 1) {
			$scope.selectedCategories = [];
			$scope.categorySelected = false;
			findParentCategories();
		} else {
			category = $scope.selectedCategories[length - 2];
			$scope.selectCategoryFromTop(category);
		}
	}

});

taskManagerModule
		.controller(
				'taskManagerController',
				function($scope, $http) {

					var urlBase = "";
					$scope.toggle = true;
					$scope.selection = [];
					$scope.statuses = [ 'ACTIVE', 'COMPLETED' ];
					$scope.priorities = [ 'HIGH', 'LOW', 'MEDIUM' ];
					$http.defaults.headers.post["Content-Type"] = "application/json";

					function findAllTasks() {
						// get all tasks and display initially
						$http
								.get(
										urlBase
												+ '/tasks/search/findByTaskArchived?archivedfalse=0')
								.success(
										function(data) {
											if (data._embedded != undefined) {
												$scope.tasks = data._embedded.tasks;
											} else {
												$scope.tasks = [];
											}
											for (var i = 0; i < $scope.tasks.length; i++) {
												if ($scope.tasks[i].taskStatus == 'COMPLETED') {
													$scope.selection
															.push($scope.tasks[i].taskId);
												}
											}
											$scope.taskName = "";
											$scope.taskDesc = "";
											$scope.taskPriority = "";
											$scope.taskStatus = "";
											$scope.toggle = '!toggle';
										});
					}

					findAllTasks();

					// add a new task
					$scope.addTask = function addTask() {
						if ($scope.taskName == "" || $scope.taskDesc == ""
								|| $scope.taskPriority == ""
								|| $scope.taskStatus == "") {
							alert("Insufficient Data! Please provide values for task name, description, priortiy and status");
						} else {
							$http.post(urlBase + '/tasks', {
								taskName : $scope.taskName,
								taskDescription : $scope.taskDesc,
								taskPriority : $scope.taskPriority,
								taskStatus : $scope.taskStatus
							}).success(
									function(data, status, headers) {
										alert("Task added");
										var newTaskUri = headers()["location"];
										console.log("Might be good to GET "
												+ newTaskUri
												+ " and append the task.");
										// Refetching EVERYTHING every time can
										// get expensive over time
										// Better solution would be to
										// $http.get(headers()["location"]) and
										// add it to the list
										findAllTasks();
									});
						}
					};

					// toggle selection for a given task by task id
					$scope.toggleSelection = function toggleSelection(taskUri) {
						var idx = $scope.selection.indexOf(taskUri);

						// is currently selected
						// HTTP PATCH to ACTIVE state
						if (idx > -1) {
							$http.patch(taskUri, {
								taskStatus : 'ACTIVE'
							}).success(function(data) {
								alert("Task unmarked");
								findAllTasks();
							});
							$scope.selection.splice(idx, 1);
						}

						// is newly selected
						// HTTP PATCH to COMPLETED state
						else {
							$http.patch(taskUri, {
								taskStatus : 'COMPLETED'
							}).success(function(data) {
								alert("Task marked completed");
								findAllTasks();
							});
							$scope.selection.push(taskUri);
						}
					};

					// Archive Completed Tasks
					$scope.archiveTasks = function archiveTasks() {
						$scope.selection.forEach(function(taskUri) {
							if (taskUri != undefined) {
								$http.patch(taskUri, {
									taskArchived : 1
								});
							}
						});
						alert("Successfully Archived");
						console
								.log("It's risky to run this without confirming all the patches are done. when.js is great for that");
						findAllTasks();
					};

				});

// Angularjs Directive for confirm dialog box
taskManagerModule.directive('ngConfirmClick', [ function() {
	return {
		link : function(scope, element, attr) {
			var msg = attr.ngConfirmClick || "Are you sure?";
			var clickAction = attr.confirmedClick;
			element.bind('click', function(event) {
				if (window.confirm(msg)) {
					scope.$eval(clickAction);
				}
			});
		}
	};
} ]);