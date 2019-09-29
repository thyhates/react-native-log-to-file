# react-native-log-to-file
Log message to file in react native, work for Android.
Get memory information, just work for Android.


## Installation
Install package from npm

```
npm install --save react-native-log-to-file
```
or
```
yarn add react-native-log-to-file
```
Link to your project
```
react-native link
```
## Usage
### log to file
```javascript
import log from 'react-native-log-to-file';
log.logToFile('app started').then(res=>{
    console.log(res);
})
```
The log file locate in 'applicationName/applicationName_log.log'.

### get used memory
```javascript
import log from 'react-native-log-to-file';
Log.getUsedMemory().then(usedMemory => {
    let used = (usedMemory / 1024 / 1024).toFixed(2);
})
```
### get max memory
```javascript
import log from 'react-native-log-to-file';
Log.getMaxMemory().then(maxMemory => {
    let max = (maxMemory / 1024 / 1024).toFixed(2);
})
```
### get total memory
```javascript
import log from 'react-native-log-to-file';
Log.getTotalMemory().then(totalMemory => {
    let total = (totalMemory / 1024 / 1024).toFixed(2);
})
```
